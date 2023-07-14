package com.hellguy39.hellnotes.core.ui.layout

import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.components.navigation.HNDrawerSheet
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationItemSelection

@Composable
fun NavigationDrawerLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    content: @Composable () -> Unit,
    onNewNoteFabClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    PermanentNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier,
                content = {
                    HNDrawerSheet(
                        navItems = navItems,
                        currentDestination = currentDestination,
                        onCloseMenuButtonClick = { },
                        onNewNoteFabClick = onNewNoteFabClick,
                        onSettingsClick = onSettingsClick,
                        onAboutClick = onAboutClick
                    )
                }
            )
        },
        content = content
    )
}