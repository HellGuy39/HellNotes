package com.hellguy39.hellnotes.core.ui.resources

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

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
