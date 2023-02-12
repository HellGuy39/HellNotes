package com.hellguy39.hellnotes.core.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

object UiDefaults {

    object Alpha {

        const val Accented = 0.85f
        const val Emphasize = 0.60f
        const val Inconspicuous = 0.35f

        // For text fields
        const val Hint = 0.66f
    }

    object Motion {

        val ScreenEnterTransition =
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))

        val ScreenExitTransition =
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))

        val ScreenPopEnterTransition =
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))

        val ScreenPopExitTransition =
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))

    }

}