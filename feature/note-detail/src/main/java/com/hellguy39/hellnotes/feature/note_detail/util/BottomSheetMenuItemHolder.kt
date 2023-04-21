package com.hellguy39.hellnotes.feature.note_detail.util

import androidx.compose.ui.graphics.painter.Painter

data class BottomSheetMenuItemHolder(
    val icon: Painter,
    val title: String = "",
    val onClick: () -> Unit = {}
)
