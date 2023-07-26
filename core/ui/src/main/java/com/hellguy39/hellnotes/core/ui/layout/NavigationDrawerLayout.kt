package com.hellguy39.hellnotes.core.ui.layout

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.component.navigation.HNDrawerSheet
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.window.rememberWindowInfo

@Composable
fun NavigationDrawerLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    content: @Composable () -> Unit,
    onNewNoteFabClick: () -> Unit,
    onCloseMenuButtonClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    val windowInfo = rememberWindowInfo()
    val width = remember { (windowInfo.screenWidth / 3).coerceAtMost(DrawerDefaults.MaximumDrawerWidth) }

    PermanentNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(width),
                content = {
                    HNDrawerSheet(
                        navItems = navItems,
                        currentDestination = currentDestination,
                        onCloseMenuButtonClick = onCloseMenuButtonClick,
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