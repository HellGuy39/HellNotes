package com.hellguy39.hellnotes.core.ui.values

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith

fun <T> AnimatedContentTransitionScope<T>.slideEnterTransition(
    duration: Int = Duration.MEDIUM,
    direction: AnimatedContentTransitionScope.SlideDirection =
        AnimatedContentTransitionScope.SlideDirection.Companion.Left,
): EnterTransition {
    return slideIntoContainer(
        towards = direction,
        animationSpec = tween(duration),
    )
}

fun <T> AnimatedContentTransitionScope<T>.slideExitTransition(
    duration: Int = Duration.FAST,
    direction: AnimatedContentTransitionScope.SlideDirection =
        AnimatedContentTransitionScope.SlideDirection.Companion.Right,
): ExitTransition {
    return slideOutOfContainer(
        towards = direction,
        animationSpec = tween(duration),
    )
}

fun <T> AnimatedContentTransitionScope<T>.slideContentTransform(
    durationEnter: Int = Duration.MEDIUM,
    durationExit: Int = Duration.FAST,
): ContentTransform {
    return slideEnterTransition(durationEnter).togetherWith(slideExitTransition(durationExit))
}

fun <T> AnimatedContentTransitionScope<T>.slideRightOnlyContentTransform(
    durationEnter: Int = Duration.MEDIUM,
    durationExit: Int = Duration.FAST,
): ContentTransform {
    return slideEnterTransition(durationEnter).togetherWith(
        slideExitTransition(
            duration = durationExit,
            direction = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
        ),
    )
}
