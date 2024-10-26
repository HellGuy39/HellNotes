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
package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HNIconButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    enabledPainter: Painter,
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledPainter: Painter? = null,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp,
) {
    if (enabled) {
        IconButton(
            modifier = Modifier.size(containerSize),
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = enabledPainter,
                tint = tint,
                contentDescription = null,
            )
        }
    } else {
        Box(
            modifier = Modifier.size(containerSize),
            contentAlignment = Alignment.Center,
        ) {
            if (disabledPainter == null) {
                Spacer(
                    modifier = Modifier.size(iconSize),
                )
            } else {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = disabledPainter,
                    tint = tint,
                    contentDescription = null,
                )
            }
        }
    }
}
