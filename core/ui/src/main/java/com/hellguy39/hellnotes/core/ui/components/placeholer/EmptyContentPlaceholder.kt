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
package com.hellguy39.hellnotes.core.ui.components.placeholer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Duration
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun EmptyContentPlaceholder(
    modifier: Modifier = Modifier,
    heroIcon: Painter,
    message: String,
    heroIconSize: Dp = 128.dp,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = heroIcon,
            contentDescription = null,
            modifier =
                Modifier
                    .size(heroIconSize),
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            textAlign = TextAlign.Center,
        )
        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        }
    }
}

@Composable
fun EmptyContentPlaceholder(
    modifier: Modifier = Modifier,
    heroIcon: UiIcon,
    message: UiText,
    heroIconSize: Dp = 128.dp,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    val animatableAlpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animatableAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = Duration.SLOW),
        )
    }

    Column(
        modifier =
            modifier.then(
                Modifier.padding(horizontal = Spaces.extraLarge)
                    .alpha(animatableAlpha.value),
            ),
        verticalArrangement =
            Arrangement.spacedBy(
                space = Spaces.medium,
                alignment = Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = heroIcon.asPainter(),
            contentDescription = null,
            modifier =
                Modifier
                    .size(heroIconSize),
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = message.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            textAlign = TextAlign.Center,
        )
        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        }
    }
}
