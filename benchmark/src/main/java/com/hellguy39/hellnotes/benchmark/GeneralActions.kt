/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.benchmark

import android.Manifest
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

/**
 * Because the app under test is different from the one running the instrumentation test,
 * the permission has to be granted manually by either:
 *
 * - tapping the Allow button
 *    ```kotlin
 *    val obj = By.text("Allow")
 *    val dialog = device.wait(Until.findObject(obj), TIMEOUT)
 *    dialog?.let {
 *        it.click()
 *        device.wait(Until.gone(obj), 5_000)
 *    }
 *    ```
 * - or (preferred) executing the grant command on the target package.
 */
fun MacrobenchmarkScope.allowNotifications() {
    if (SDK_INT >= TIRAMISU) {
        val command = "pm grant $packageName ${Manifest.permission.POST_NOTIFICATIONS}"
        device.executeShellCommand(command)
    }
}

/**
 * Wraps starting the default activity, waiting for it to start and then allowing notifications in
 * one convenient call.
 */
fun MacrobenchmarkScope.startActivityAndAllowNotifications() {
    startActivityAndWait()
    allowNotifications()
}

/**
 * Waits for and returns the `niaTopAppBar`
 */
fun MacrobenchmarkScope.getTopAppBar(): UiObject2 {
    device.wait(Until.hasObject(By.res("hnTopAppBar")), 2_000)
    return device.findObject(By.res("hnTopAppBar"))
}

/**
 * Waits for an object on the top app bar, passed in as [selector].
 */
fun MacrobenchmarkScope.waitForObjectOnTopAppBar(selector: BySelector, timeout: Long = 2_000) {
    getTopAppBar().wait(Until.hasObject(selector), timeout)
}

fun MacrobenchmarkScope.completeOnBoarding() {
    val nextButtonRes = By.res("button_next")
    device.wait(Until.hasObject(nextButtonRes), 2_000)

    while (device.hasObject(nextButtonRes)) {
        val nextButton = device.findObject(nextButtonRes)
        nextButton.click()
        device.waitFor(500)
    }
}

fun MacrobenchmarkScope.skipOnBoarding() {
    val skipButtonRes = By.res("button_skip")
    device.wait(Until.hasObject(skipButtonRes), 2_000)
    if (device.hasObject(skipButtonRes)) {
        val skipButton = device.findObject(skipButtonRes)
        skipButton.click()
        device.waitFor(500)
    }
}

fun MacrobenchmarkScope.createNote() {
    val fabCreateNote = device.waitAndFindObject(By.res("fab_create_note"), 2_000)
    fabCreateNote.click()

    device.waitFor(500)

    val tfTitle = device.waitAndFindObject(By.res("tf_title"), 2_000)
    val tfContent = device.waitAndFindObject(By.res("tf_content"), 2_000)

    tfTitle.text = "title 1"
    tfContent.text = "title 2"

    device.waitFor(500)

    val backButton = device.waitAndFindObject(By.res("button_back"), 2_000)
    backButton.click()

    device.waitFor(500)
}
