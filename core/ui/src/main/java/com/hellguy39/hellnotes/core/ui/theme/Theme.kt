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
package com.hellguy39.hellnotes.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.api.ApiCapabilities
import com.hellguy39.hellnotes.core.model.ColorMode
import com.hellguy39.hellnotes.core.model.Theme
import com.hellguy39.hellnotes.core.ui.theme.amoledblack.amoledBlackColorSchema
import com.hellguy39.hellnotes.core.ui.theme.classicblue.defaultColorSchema
import com.hellguy39.hellnotes.core.ui.theme.dynamic.dynamicColorSchema

@Composable
fun HellNotesTheme(
    themeViewModel: AppThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    val themeUiState by themeViewModel.uiState.collectAsStateWithLifecycle()

    val colorMode = themeUiState.colorMode
    val darkTheme = if (themeUiState.theme == Theme.System) isSystemInDarkTheme() else themeUiState.theme == Theme.Dark

    val colorScheme = when {
        colorMode is ColorMode.MaterialYou && ApiCapabilities.dynamicColorAvailable -> dynamicColorSchema(context, darkTheme)
        colorMode is ColorMode.Default -> defaultColorSchema(darkTheme)
        colorMode is ColorMode.AmoledBlack -> amoledBlackColorSchema()
        else -> defaultColorSchema(darkTheme)
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

@Composable
fun HellNotesThemePreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> defaultColorSchema(true)
            else -> defaultColorSchema(false)
        }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
