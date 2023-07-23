package com.hellguy39.hellnotes.feature.home.util

import androidx.annotation.DrawableRes
import com.hellguy39.hellnotes.core.ui.text.UiText

data class BottomSheetMenuItemSelection(
    @DrawableRes val iconId: Int,
    val title: UiText = UiText.Empty,
    val onClick: () -> Unit = {}
)
