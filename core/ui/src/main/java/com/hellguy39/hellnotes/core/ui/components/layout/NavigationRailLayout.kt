package com.hellguy39.hellnotes.core.ui.components.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationRailItem
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun NavigationRailLayout(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    content: @Composable () -> Unit,
    onNewNoteFabClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        NavigationRail(
            header = {
                //Spacer(modifier = Modifier.height(0.dp))
                IconButton(onClick = {}) {
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
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote)
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
        content()
    }
}
