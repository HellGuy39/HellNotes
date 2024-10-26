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
package com.hellguy39.hellnotes.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo =
            when {
                configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
                configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
                else -> WindowInfo.WindowType.Expanded
            },
        screenHeightInfo =
            when {
                configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compact
                configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
                else -> WindowInfo.WindowType.Expanded
            },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp,
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp,
) {
    sealed class WindowType {
        object Compact : WindowType()

        object Medium : WindowType()

        object Expanded : WindowType()
    }
}
