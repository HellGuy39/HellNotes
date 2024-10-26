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
