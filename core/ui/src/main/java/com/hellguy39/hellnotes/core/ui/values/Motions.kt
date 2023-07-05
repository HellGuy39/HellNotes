package com.hellguy39.hellnotes.core.ui.values

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

class Motions {

    companion object {
        const val DEFAULT_DURATION = 300

        const val FAST_DURATION = 250
        const val MEDIUM_DURATION = 300
        const val SLOW_DURATION = 400
    }

    fun slideEnter(duration: Int = DEFAULT_DURATION) =
        slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = duration)
        ) + fadeIn(animationSpec = tween(durationMillis = duration))

    fun slideExit(duration: Int = DEFAULT_DURATION) =
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = duration)
        ) + fadeOut(animationSpec = tween(durationMillis = duration))

    fun slidePopEnter(duration: Int = DEFAULT_DURATION) =
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = duration)
        ) + fadeIn(animationSpec = tween(durationMillis = duration))

    fun slidePopExit(duration: Int = DEFAULT_DURATION) =
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = duration)
        ) + fadeOut(animationSpec = tween(durationMillis = duration))

    fun fadeEnter(duration: Int = DEFAULT_DURATION) =
        fadeIn(animationSpec = tween(durationMillis = duration))

    fun fadeExit(duration: Int = DEFAULT_DURATION) =
        fadeOut(animationSpec = tween(durationMillis = duration))

    fun fadePopEnter(duration: Int = DEFAULT_DURATION) =
        fadeIn(animationSpec = tween(durationMillis = duration))

    fun fadePopExit(duration: Int = DEFAULT_DURATION) =
        fadeOut(animationSpec = tween(durationMillis = duration))

}

val LocalMotions = compositionLocalOf { Motions() }

val MaterialTheme.motions: Motions
    @Composable
    @ReadOnlyComposable
    get() = LocalMotions.current