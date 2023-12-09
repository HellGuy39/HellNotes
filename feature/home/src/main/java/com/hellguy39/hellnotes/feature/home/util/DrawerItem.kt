package com.hellguy39.hellnotes.feature.home.util

import androidx.compose.ui.graphics.painter.Painter

data class DrawerItem(
    val title: String = "",
    val icon: Painter? = null,
    val itemType: DrawerItemType = DrawerItemType.None,
    val onClick: (item: DrawerItem) -> Unit = {},
)

sealed class DrawerItemType {
    object Primary : DrawerItemType()

    object Secondary : DrawerItemType()

    object Label : DrawerItemType()

    object Static : DrawerItemType()

    object None : DrawerItemType()
}
