package com.hellguy39.hellnotes.core.ui.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.component.navigation.HNDrawerSheet
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationRail
import kotlinx.coroutines.launch

@Composable
fun NavigationRailLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    content: @Composable () -> Unit,
    onNewNoteFabClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    drawerState: DrawerState,
    onDrawerOpen: (Boolean) -> Unit
) {
    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = DrawerDefaults.scrimColor,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier,
                content = {
                    HNDrawerSheet(
                        navItems = navItems,
                        currentDestination = currentDestination,
                        onCloseMenuButtonClick = { onDrawerOpen(false) },
                        onNewNoteFabClick = onNewNoteFabClick,
                        onItemClick = { onDrawerOpen(false) },
                        onSettingsClick = onSettingsClick,
                        onAboutClick = onAboutClick
                    )
                }
            )
        },
        content = {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                HNNavigationRail(
                    navItems = navItems,
                    currentDestination = currentDestination,
                    onNavigationButtonClick = { onDrawerOpen(true) },
                    onNewNoteFabClick = onNewNoteFabClick
                )
                content()
            }
        }
    )
}
