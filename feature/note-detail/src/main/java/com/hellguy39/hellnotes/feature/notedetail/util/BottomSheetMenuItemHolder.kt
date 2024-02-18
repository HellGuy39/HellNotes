package com.hellguy39.hellnotes.feature.notedetail.util

import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText

data class BottomSheetMenuItemHolder(
    val icon: UiIcon = UiIcon.Empty,
    val title: UiText = UiText.Empty,
    val onClick: () -> Unit = {},
)
