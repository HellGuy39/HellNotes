package com.hellguy39.hellnotes.feature.home.util

import androidx.compose.ui.graphics.painter.Painter

data class DrawerItem(
    val title: String = "",
    val icon: Painter? = null,
    val itemType: DrawerItemType = DrawerItemType.None,
    val route: String = "",
    val onClick: (item: DrawerItem) -> Unit = {},
)

sealed class DrawerItemType {
    data object Primary : DrawerItemType()

    data object Secondary : DrawerItemType()

    data object Label : DrawerItemType()

    data object Static : DrawerItemType()

    data object None : DrawerItemType()
}
