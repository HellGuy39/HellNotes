package com.hellguy39.hellnotes.core.ui.value

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

data class Alpha(
    val level0: Float = 0.0f,
    val level1: Float = 0.33f,
    val level2: Float = 0.66f, // For text fields
    val level3: Float = 0.85f,
    val level4: Float = 1.0f
)

val LocalAlpha = compositionLocalOf { Alpha() }

val MaterialTheme.alpha: Alpha
    @Composable
    @ReadOnlyComposable
    get() = LocalAlpha.current