package com.hellguy39.hellnotes.feature.settings.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons

@Composable
fun getSettingsNavigationItems(onItemClick: (HNNavigationItemSelection) -> Unit) = listOf(
    HNNavigationItemSelection(
        screen = GraphScreen.Settings.General,
        title = "General",
        subtitle = "some text",
        icon = painterResource(id = HellNotesIcons.Info),
        onClick = onItemClick
    ),
    HNNavigationItemSelection(
        screen = GraphScreen.Settings.Security,
        title = "Security",
        subtitle = "some text",
        icon = painterResource(id = HellNotesIcons.Info),
        onClick = onItemClick
    ),
    HNNavigationItemSelection(
        screen = GraphScreen.Settings.Theme,
        title = "Theme",
        subtitle = "some text",
        icon = painterResource(id = HellNotesIcons.Info),
        onClick = onItemClick
    ),
)