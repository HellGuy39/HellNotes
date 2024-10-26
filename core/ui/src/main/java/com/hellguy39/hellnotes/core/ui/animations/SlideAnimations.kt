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
package com.hellguy39.hellnotes.core.ui.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import com.hellguy39.hellnotes.core.ui.values.Duration

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
