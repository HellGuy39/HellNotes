package com.hellguy39.hellnotes.feature.home.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.text.UiText

@Composable
fun rememberHomeNavigationItems(onItemClick: (HNNavigationItemSelection) -> Unit) = remember {
    listOf(
        HNNavigationItemSelection(
            screen = GraphScreen.Main.Notes,
            title = UiText.StringResources(HellNotesStrings.Title.Notes),
            iconId = HellNotesIcons.StickyNote,
            onClick = onItemClick
        ),
        HNNavigationItemSelection(
            screen = GraphScreen.Main.Reminders,
            title = UiText.StringResources(HellNotesStrings.Title.Reminders),
            iconId = HellNotesIcons.Notifications,
            onClick = onItemClick
        ),
//    HNNavigationItemSelection(
//        screen = GraphScreen.Main.Labels,
//        title = stringResource(id = HellNotesStrings.Title.Labels),
//        icon = painterResource(id = HellNotesIcons.Label),
//        onClick = onItemClick
//    ),
        HNNavigationItemSelection(
            screen = GraphScreen.Main.Archive,
            title = UiText.StringResources(HellNotesStrings.Title.Archive),
            iconId = HellNotesIcons.Archive,
            onClick = onItemClick
        ),
        HNNavigationItemSelection(
            screen = GraphScreen.Main.Trash,
            title = UiText.StringResources(HellNotesStrings.Title.Trash),
            iconId = HellNotesIcons.Delete,
            onClick = onItemClick
        ),
    )
}