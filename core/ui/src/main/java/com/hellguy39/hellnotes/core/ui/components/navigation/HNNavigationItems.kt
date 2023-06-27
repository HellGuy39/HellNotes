package com.hellguy39.hellnotes.core.ui.components.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.hellguy39.hellnotes.core.ui.navigations.Screen

@Composable
fun RowScope.HNNavigationBarItem(
    item: HNNavigationItemSelection,
    currentDestination: NavDestination?
) {
    NavigationBarItem(
        icon = {
            item.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge
            )
        },
        selected = currentDestination?.hierarchy?.any { navDestination ->
            navDestination.route == item.screen.route
        } ?: false,
        onClick = { item.onClick(item) },
    )
}

@Composable
fun ColumnScope.HNNavigationRailItem(
    item: HNNavigationItemSelection,
    currentDestination: NavDestination?
) {
    val isSelected = currentDestination?.hierarchy?.any { navDestination ->
        navDestination.route == item.screen.route
    } ?: false

    NavigationRailItem(
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        alwaysShowLabel = isSelected,
        icon = {
            item.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge
            )
        },
        selected = isSelected,
        onClick = { item.onClick(item) },
    )
}

@Composable
fun ColumnScope.HNNavigationDrawerItem(
    item: HNNavigationItemSelection,
    currentDestination: NavDestination?
) {
    NavigationDrawerItem(
        icon = {
            item.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge
            )
        },
        selected = currentDestination?.hierarchy?.any { navDestination ->
            navDestination.route == item.screen.route
        } ?: false,
        onClick = { item.onClick(item) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

data class HNNavigationItemSelection(
    val screen: Screen,
    val title: String = "",
    val icon: Painter? = null,
    val onClick: (item: HNNavigationItemSelection) -> Unit = {}
)

//@Composable
//fun HNNavigationDrawer(
//    drawerState: DrawerState,
//    drawerSheet: @Composable () -> Unit,
//    content: @Composable () -> Unit
//) {
//    val windowInfo = rememberWindowInfo()
//
//    HNModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerSheet = drawerSheet,
//        content = content
//    )
//
////    when(windowInfo.screenWidthInfo) {
////        is WindowInfo.WindowType.Compact -> {
////            HNModalNavigationDrawer(
////                drawerState = drawerState,
////                drawerSheet = drawerSheet,
////                content = content
////            )
////        }
////        else -> {
////            HNPermanentNavigationDrawer(
////                drawerSheet = drawerSheet,
////                content = content
////            )
////        }
////    }
//
//}
//
//@Composable
//private fun HNPermanentNavigationDrawer(
//    drawerSheet: @Composable () -> Unit,
//    content: @Composable () -> Unit
//) {
//    PermanentNavigationDrawer(
//        modifier = Modifier,
//        drawerContent = drawerSheet,
//        content = content
//    )
//}
//
//@Composable
//private fun HNModalNavigationDrawer(
//    drawerState: DrawerState,
//    drawerSheet: @Composable () -> Unit,
//    content: @Composable () -> Unit
//) {
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = drawerSheet,
//        content = content
//    )
//}