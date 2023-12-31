package com.hellguy39.hellnotes.feature.home.util

import com.hellguy39.hellnotes.core.ui.resources.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.UiText

data class DrawerItem(
    val title: UiText = UiText.Empty,
    val icon: UiIcon = UiIcon.Empty,
    val route: String = "",
    val onClick: (item: DrawerItem) -> Unit = {},
)
