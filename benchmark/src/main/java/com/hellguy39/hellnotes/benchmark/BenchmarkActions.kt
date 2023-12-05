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