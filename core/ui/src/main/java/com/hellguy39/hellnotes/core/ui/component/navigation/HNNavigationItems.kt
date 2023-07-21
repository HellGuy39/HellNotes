package com.hellguy39.hellnotes.core.ui.component.navigation

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
import com.hellguy39.hellnotes.core.ui.model.Screen

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
fun HNNavigationRailItem(
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
fun HNNavigationDrawerItem(
    item: HNNavigationItemSelection,
    currentDestination: NavDestination?,
    onItemClick: () -> Unit = {}
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
        selected = currentDestination
            ?.hierarchy
            ?.any { navDestination ->
                navDestination.route == item.screen.route
            } ?: false,
        onClick = {
            item.onClick(item)
            onItemClick()
        },
        colors = NavigationDrawerItemDefaults.colors(),
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

data class HNNavigationItemSelection(
    val screen: Screen,
    val title: String = "",
    val subtitle: String = "",
    val icon: Painter? = null,
    val onClick: (item: HNNavigationItemSelection) -> Unit = {}
)