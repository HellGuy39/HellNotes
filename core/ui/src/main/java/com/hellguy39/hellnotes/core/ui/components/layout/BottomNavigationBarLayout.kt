package com.hellguy39.hellnotes.core.ui.components.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationBarItem
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun BottomNavigationBarLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    isVisible: Boolean = true,
    content: @Composable (innerPadding: PaddingValues) -> Unit,
    onNewNoteFabClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(visible = isVisible) {
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
            AnimatedVisibility(visible = isVisible) {
                NavigationBar(
                    content = {
                        navItems.forEach { item ->
                            HNNavigationBarItem(
                                item = item,
                                currentDestination = currentDestination
                            )
                        }
                    }
                )
            }
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}
