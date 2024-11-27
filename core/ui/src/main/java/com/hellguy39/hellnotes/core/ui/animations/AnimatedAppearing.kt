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

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hellguy39.hellnotes.core.ui.values.Duration

@Composable
fun AnimatedAppearing(
    lazyListState: LazyListState,
    index: Int,
    content: @Composable () -> Unit,
) {
    val animatableAlpha = remember { Animatable(0f) }
    val isVisible =
        remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex <= index
            }
        }

    LaunchedEffect(isVisible.value) {
        if (isVisible.value) {
            animatableAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = Duration.SLOW),
            )
        }
    }

    Box(
        modifier = Modifier.alpha(animatableAlpha.value),
    ) {
        content()
    }
}
