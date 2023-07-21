package com.hellguy39.hellnotes.core.ui.component.navigation

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons

@Composable
fun HNNavigationRail(
    onNavigationButtonClick: () -> Unit = {},
    onNewNoteFabClick: () -> Unit = {},
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
) = NavigationRail(
    header = {
        IconButton(onClick = onNavigationButtonClick) {
            Icon(
                painter = painterResource(id = HellNotesIcons.Menu),
                contentDescription = null
            )
        }
        FloatingActionButton(
            onClick = onNewNoteFabClick,
            content = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Add),
                    contentDescription = null
                )
            },
            elevation = FloatingActionButtonDefaults.loweredElevation()
        )
    },
    content = {
        navItems.forEach { item ->
            HNNavigationRailItem(
                item = item,
                currentDestination = currentDestination
            )
        }
    }
)