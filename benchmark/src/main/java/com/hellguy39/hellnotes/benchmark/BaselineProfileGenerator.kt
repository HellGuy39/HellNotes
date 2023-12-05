package com.hellguy39.hellnotes.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineRule = BaselineProfileRule()

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun generateBaselineProfile() = baselineRule.collect(
        packageName = "com.hellguy39.hellnotes"
    ) {
        pressHome()
        startActivityAndWait()
//        enterOnBoarding()
//        createNotes()
//        scrollDownAndUp()
    }

}