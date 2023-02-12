package com.hellguy39.hellnotes.feature.lock_selection.util

import androidx.compose.ui.graphics.painter.Painter
import com.hellguy39.hellnotes.core.model.util.LockScreenType

data class LockScreenTypeItemHolder(
    val title: String,
    val icon: Painter,
    val lockScreenType: LockScreenType
)