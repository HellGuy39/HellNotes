package com.hellguy39.hellnotes.feature.settings.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.text.UiText

@Composable
fun rememberSettingsNavigationItems(onItemClick: (HNNavigationItemSelection) -> Unit) = remember {
    listOf(
        HNNavigationItemSelection(
            screen = GraphScreen.Settings.General,
            title = UiText.DynamicString("General"),
            subtitle = UiText.DynamicString("Language, backup"),
            iconId = HellNotesIcons.Info,
            onClick = onItemClick
        ),
        HNNavigationItemSelection(
            screen = GraphScreen.Settings.Security,
            title = UiText.DynamicString("Security"),
            subtitle = UiText.DynamicString("Screen lock, biometric"),
            iconId = HellNotesIcons.Info,
            onClick = onItemClick
        ),
        HNNavigationItemSelection(
            screen = GraphScreen.Settings.Appearance,
            title = UiText.DynamicString("Appearance"),
            subtitle = UiText.DynamicString("Theme, dynamic color"),
            iconId = HellNotesIcons.Info,
            onClick = onItemClick
        ),
    )
}