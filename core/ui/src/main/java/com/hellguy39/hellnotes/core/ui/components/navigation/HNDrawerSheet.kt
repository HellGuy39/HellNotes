package com.hellguy39.hellnotes.core.ui.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.spacing


@Composable
fun HNDrawerSheet(
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    onCloseMenuButtonClick: () -> Unit = {},
    onNewNoteFabClick: () -> Unit = {},
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(
                top = 10.dp,
                start = MaterialTheme.spacing.medium,
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = HellNotesStrings.AppName),
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(onClick = onCloseMenuButtonClick) {
            Icon(
                painter = painterResource(id = HellNotesIcons.MenuOpen),
                contentDescription = null
            )
        }
    }

    ExtendedFloatingActionButton(
        modifier = Modifier.fillMaxWidth()
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
            currentDestination = currentDestination,
            onItemClick = onItemClick
        )
    }



}