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

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction

fun MacrobenchmarkScope.enterOnBoarding() {
    device.waitForIdle(15000)

    val nextButton = device.findObject(By.res("button_next"))
    val skipButton = device.findObject(By.res("button_skip"))

    skipButton.click()

//    while (device.hasObject(By.text(context.getString(HellNotesStrings.Button.Skip)))) {
//        nextButton.click()
//        device.waitForIdle(300)
//    }
//    nextButton.click()
}

fun MacrobenchmarkScope.createNotes() {
}

fun MacrobenchmarkScope.scrollDownAndUp(repeats: Int = 2) {
    val list = device.findObject(By.res("item_list"))

    list.setGestureMargin(device.displayWidth / 5)

    repeat(repeats) {
        list.fling(Direction.DOWN)
        list.fling(Direction.UP)
    }
}

fun MacrobenchmarkScope.goBack() {
}

fun MacrobenchmarkScope.enterSettings() {
}

fun MacrobenchmarkScope.enterAboutApp() {
}
