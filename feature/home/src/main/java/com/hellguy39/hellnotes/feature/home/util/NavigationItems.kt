package com.hellguy39.hellnotes.feature.home.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun getHomeNavigationItems(onItemClick: (HNNavigationItemSelection) -> Unit) = listOf(
    HNNavigationItemSelection(
        screen = GraphScreen.Main.Notes,
        title = stringResource(id = HellNotesStrings.Title.Notes),
        icon = painterResource(id = HellNotesIcons.StickyNote),
        onClick = onItemClick
    ),
    HNNavigationItemSelection(
        screen = GraphScreen.Main.Reminders,
        title = stringResource(id = HellNotesStrings.Title.Reminders),
        icon = painterResource(id = HellNotesIcons.Notifications),
        onClick = onItemClick
    ),
    HNNavigationItemSelection(
        screen = GraphScreen.Main.Labels,
        title = stringResource(id = HellNotesStrings.Title.Labels),
        icon = painterResource(id = HellNotesIcons.Label),
        onClick = onItemClick
    ),
    HNNavigationItemSelection(
        screen = GraphScreen.Main.Archive,
        title = stringResource(id = HellNotesStrings.Title.Archive),
        icon = painterResource(id = HellNotesIcons.Archive),
        onClick = onItemClick
    ),
    HNNavigationItemSelection(
        screen = GraphScreen.Main.Trash,
        title = stringResource(id = HellNotesStrings.Title.Trash),
        icon = painterResource(id = HellNotesIcons.Delete),
        onClick = onItemClick
    ),
)