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

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.values.Alpha

@Composable
fun CustomDivider(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    isVisible: Boolean = true,
    alpha: Float = Alpha.LEVEL_2,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.surfaceTint,
) {
    val transition = updateTransition(isVisible, label = "transition")

    val animatedColor by transition.animateColor(label = "animatedColor") { state ->
        when (state) {
            true -> color
            false -> Color.Transparent
        }
    }

    Divider(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .alpha(alpha),
        thickness = thickness,
        color = animatedColor,
    )
}
