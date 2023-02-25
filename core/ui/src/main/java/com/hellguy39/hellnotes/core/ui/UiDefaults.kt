package com.hellguy39.hellnotes.core.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.dp

object UiDefaults {

    object Elevation {
        val Level0 = 0.dp
        val Level1 = 1.dp
        val Level2 = 3.dp
        val Level3 = 6.dp
        val Level4 = 8.dp
        val Level5 = 12.dp
    }

    object Alpha {

        const val Accented = 0.85f
        const val Emphasize = 0.60f
        const val Inconspicuous = 0.35f

        // For text fields
        const val Hint = 0.66f
    }

    object Motion {

        private const val FAST_DURATION = 200
        private const val MEDIUM_DURATION = 300
        private const val SLOW_DURATION = 400

        val ScreenEnterTransition =
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = MEDIUM_DURATION)
            ) + fadeIn(animationSpec = tween(durationMillis = MEDIUM_DURATION))

        val ScreenExitTransition =
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = MEDIUM_DURATION)
            ) + fadeOut(animationSpec = tween(durationMillis = MEDIUM_DURATION))

        val ScreenPopEnterTransition =
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = MEDIUM_DURATION)
            ) + fadeIn(animationSpec = tween(durationMillis = MEDIUM_DURATION))

        val ScreenPopExitTransition =
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = MEDIUM_DURATION)
            ) + fadeOut(animationSpec = tween(durationMillis = MEDIUM_DURATION))

    }

}