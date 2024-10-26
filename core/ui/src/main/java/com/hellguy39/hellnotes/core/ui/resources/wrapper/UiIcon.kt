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
package com.hellguy39.hellnotes.core.ui.resources.wrapper

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.hellguy39.hellnotes.core.ui.resources.AppIcons

sealed class UiIcon {
    data object Empty : UiIcon()

    data class DrawableResources(
        @DrawableRes val id: Int,
    ) : UiIcon()

    @Composable
    fun asPainter(): Painter {
        return when (this) {
            is Empty -> painterResource(id = AppIcons.Image)
            is DrawableResources -> painterResource(id = id)
        }
    }
}
