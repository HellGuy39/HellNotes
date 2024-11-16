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
package com.hellguy39.hellnotes.benchmark.createnote

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import com.hellguy39.hellnotes.benchmark.BaselineProfileMetrics
import com.hellguy39.hellnotes.benchmark.PACKAGE_NAME
import com.hellguy39.hellnotes.benchmark.createNote
import com.hellguy39.hellnotes.benchmark.flingElementDownUp
import com.hellguy39.hellnotes.benchmark.skipOnBoarding
import com.hellguy39.hellnotes.benchmark.waitAndFindObject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CreateNoteBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun createNoteWithoutPreCompilation() = createNote(CompilationMode.None())

    @Test
    fun createNoteWithPartialCompilationAndDisabledBaselineProfile() =
        createNote(
            CompilationMode.Partial(baselineProfileMode = BaselineProfileMode.Disable, warmupIterations = 1),
        )

    @Test
    fun createNotePrecompiledWithBaselineProfile() =
        createNote(CompilationMode.Partial(baselineProfileMode = BaselineProfileMode.Require))

    @Test
    fun createNoteFullyPrecompiled() = createNote(CompilationMode.Full())

    private fun createNote(compilationMode: CompilationMode) =
        benchmarkRule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = BaselineProfileMetrics.allMetrics,
            compilationMode = compilationMode,
            // More iterations result in higher statistical significance.
            iterations = 20,
            startupMode = StartupMode.COLD,
            setupBlock = {
                pressHome()
                startActivityAndWait()
                skipOnBoarding()
            },
        ) {
            startActivityAndWait()
            createNote()
            device.flingElementDownUp(device.waitAndFindObject(By.res("item_list"), 2_000))
        }
}
