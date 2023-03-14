package com.hellguy39.hellnotes.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollMainScreen() = benchmarkRule.measureRepeated(
        packageName = "com.hellguy39.hellnotes",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
        scrollDownAndUp()
    }

    private fun MacrobenchmarkScope.scrollDownAndUp() {
        val list = device.findObject(By.res("item_list"))

        list.setGestureMargin(device.displayWidth / 5)

        repeat(2) {
            list.fling(Direction.DOWN)
            list.fling(Direction.UP)
        }
    }

}