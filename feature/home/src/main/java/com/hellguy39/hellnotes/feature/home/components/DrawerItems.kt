/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.util.DrawerItem

@Composable
fun rememberDrawerItems(
    onItemClick: (item: DrawerItem) -> Unit = {},
): SnapshotStateList<DrawerItem> {
    return remember {
        mutableStateListOf(
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
