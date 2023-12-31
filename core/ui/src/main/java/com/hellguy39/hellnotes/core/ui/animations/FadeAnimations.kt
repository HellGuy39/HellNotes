package com.hellguy39.hellnotes.core.ui.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.hellguy39.hellnotes.core.ui.values.Duration

fun <T> AnimatedContentTransitionScope<T>.fadeEnterTransition(
    duration: Int = Duration.MEDIUM,
    initialAlpha: Float = 0.5f,
): EnterTransition {
    return fadeIn(
        initialAlpha = initialAlpha,
        animationSpec = tween(duration),
    )
}

fun <T> AnimatedContentTransitionScope<T>.fadeExitTransition(
    duration: Int = Duration.FAST,
    targetAlpha: Float = 0f,
): ExitTransition {
    return fadeOut(
        targetAlpha = targetAlpha,
        animationSpec = tween(duration),
    )
}
