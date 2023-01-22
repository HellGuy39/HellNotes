package com.hellguy39.hellnotes.feature.home.util

import androidx.compose.ui.graphics.painter.Painter

data class DrawerItem(
    val title: String = "",
    val icon: Painter? = null,
    val onClick: (item: DrawerItem) -> Unit = {}
)
