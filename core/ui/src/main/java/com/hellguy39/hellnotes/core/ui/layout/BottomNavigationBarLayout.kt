package com.hellguy39.hellnotes.core.ui.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.component.navigation.HNDrawerSheet
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationBarItem
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationBarLayout(
    bottomNavItems: List<HNNavigationItemSelection>,
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    isVisible: Boolean = true,
    content: @Composable () -> Unit,
    drawerState: DrawerState,
    onDrawerOpen: (Boolean) -> Unit,
    onNewNoteFabClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
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
            Scaffold(
                floatingActionButton = {

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInVertically (
                            initialOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(durationMillis = 250)
                        ) + fadeIn(animationSpec = tween(durationMillis = 250)),
                        exit = slideOutVertically (
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(durationMillis = 250)
                        ) + fadeOut(animationSpec = tween(durationMillis = 250))
                    ) {
                        ExtendedFloatingActionButton(
                            text = { Text(text = stringResource(id = HellNotesStrings.Button.NewNote)) },
                            icon = {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Add),
                                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote)
                                )
                            },
                            onClick = onNewNoteFabClick,
                            expanded = true
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                bottomBar = {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInVertically (
                                initialOffsetY = { fullWidth -> fullWidth },
                                animationSpec = tween(durationMillis = 250)
                            ) + fadeIn(animationSpec = tween(durationMillis = 250)),
                        exit = slideOutVertically (
                            targetOffsetY = { fullWidth -> fullWidth },
                            animationSpec = tween(durationMillis = 250)
                        ) + fadeOut(animationSpec = tween(durationMillis = 250))
                    ) {
                        NavigationBar(
                            content = {
                                bottomNavItems.forEach { item ->
                                    HNNavigationBarItem(
                                        item = item,
                                        currentDestination = currentDestination
                                    )
                                }
                            }
                        )
                    }
//                    if (!isVisible)
//                        return@Scaffold
//
//                    NavigationBar(
//                        content = {
//                            bottomNavItems.forEach { item ->
//                                HNNavigationBarItem(
//                                    item = item,
//                                    currentDestination = currentDestination
//                                )
//                            }
//                        }
//                    )
                },
                content = { innerPadding ->
                    content(
//                        paddingValues,
//                        onDrawerOpen = {
//                            scope.launch {
//                                drawerState.open()
//                            }
//                        }
                    )
                }
            )
        }
    )
}
