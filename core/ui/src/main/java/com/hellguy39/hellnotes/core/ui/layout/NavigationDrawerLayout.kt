package com.hellguy39.hellnotes.core.ui.layout

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.component.navigation.HNDrawerSheet
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection

@Composable
fun NavigationDrawerLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    windowWidthSize: WindowWidthSizeClass,
    content: @Composable () -> Unit,
    onNewNoteFabClick: () -> Unit,
    onCloseMenuButtonClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val width = remember {
        (configuration.screenWidthDp / 4).dp
            .coerceAtMost(DrawerDefaults.MaximumDrawerWidth)
    }

    PermanentNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(width),
//                drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
//                drawerShape = DrawerDefaults.shape,
                //drawerContentColor = MaterialTheme.colorScheme.primaryContainer,
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