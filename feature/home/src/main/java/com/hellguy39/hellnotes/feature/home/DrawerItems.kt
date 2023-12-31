package com.hellguy39.hellnotes.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.UiText
import com.hellguy39.hellnotes.feature.home.util.DrawerItem

@Composable
fun rememberDrawerItems(
    onItemClick: (item: DrawerItem) -> Unit = {},
): List<DrawerItem> {
    return remember {
        listOf(
            DrawerItem(
                title = UiText.StringResources(AppStrings.Title.Notes),
                icon = UiIcon.DrawableResources(AppIcons.StickyNote),
                onClick = { onItemClick(it) },
                route = Screen.Notes.route,
            ),
            DrawerItem(
                title = UiText.StringResources(AppStrings.Title.Reminders),
                icon = UiIcon.DrawableResources(AppIcons.Notifications),
                onClick = { onItemClick(it) },
                route = Screen.Reminders.route,
            ),
            DrawerItem(
                title = UiText.StringResources(AppStrings.Title.Archive),
                icon = UiIcon.DrawableResources(AppIcons.Archive),
                onClick = { onItemClick(it) },
                route = Screen.Archive.route,
            ),
            DrawerItem(
                title = UiText.StringResources(AppStrings.Title.Trash),
                icon = UiIcon.DrawableResources(AppIcons.Delete),
                onClick = { onItemClick(it) },
                route = Screen.Trash.route,
            ),
        )
    }
}
