package com.hellguy39.hellnotes.core.ui.components.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationDrawerItem
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun NavigationDrawerLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    content: @Composable () -> Unit,
    onNewNoteFabClick: () -> Unit
) {
    PermanentNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            PermanentDrawerSheet(
                content = {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = {
                            Text(text = stringResource(id = HellNotesStrings.Button.NewNote))
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.Add),
                                contentDescription = null
                            )
                        },
                        onClick = onNewNoteFabClick,
                        expanded = true,
                        elevation = FloatingActionButtonDefaults.loweredElevation()
                    )
                    navItems.forEach { item ->
                        HNNavigationDrawerItem(
                            item = item,
                            currentDestination = currentDestination
                        )
                    }
                }
            )
        },
        content = { content() }
    )
}