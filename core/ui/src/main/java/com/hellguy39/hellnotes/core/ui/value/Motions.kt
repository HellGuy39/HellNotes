package com.hellguy39.hellnotes.core.ui.value

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object Motions {

    const val DEFAULT_DURATION = 300

    const val FAST_DURATION = 250
    const val MEDIUM_DURATION = 300
    const val SLOW_DURATION = 400

    object Screen {
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

}