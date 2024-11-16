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
package com.hellguy39.hellnotes.feature.home.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.util.DrawerItem

@Composable
fun DrawerSheetContent(
    currentDestination: NavDestination?,
    drawerItems: SnapshotStateList<DrawerItem>,
    labelItems: SnapshotStateList<DrawerItem>,
    updateState: UpdateState,
    showReviewButton: Boolean,
    onReviewButtonClick: () -> Unit,
    onUpdateButtonClick: () -> Unit,
    onManageLabelsClick: () -> Unit,
    onCreateNewLabelClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 8.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                    text =
                        buildAnnotatedString {
                            append("Hell")
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            ) {
                                append("Notes")
                            }
                        },
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            drawerItems.forEach { item ->
                CustomNavDrawerItem(
                    drawerItem = item,
                    currentDestination = currentDestination,
                )
            }

            Divider(
                modifier =
                    Modifier
                        .alpha(0.5f)
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = AppStrings.Label.Labels),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            labelItems.forEach { item ->
                CustomNavDrawerItem(
                    drawerItem = item,
                    currentDestination = currentDestination,
                )
            }

            CustomNavDrawerItem(
                drawerItem =
                    DrawerItem(
                        title = UiText.StringResources(AppStrings.MenuItem.ManageLabels),
                        icon = UiIcon.DrawableResources(AppIcons.Settings),
                        onClick = { onManageLabelsClick() },
                    ),
                currentDestination = currentDestination,
            )

            CustomNavDrawerItem(
                drawerItem =
                    DrawerItem(
                        title = UiText.StringResources(AppStrings.MenuItem.CreateNewLabel),
                        icon = UiIcon.DrawableResources(AppIcons.Add),
                        onClick = { onCreateNewLabelClick() },
                    ),
                currentDestination = currentDestination,
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp),
            ) {
                if (updateState !is UpdateState.Unavailable) {
                    OutlinedButton(
                        modifier = Modifier.padding(horizontal = 4.dp).weight(1f),
                        onClick = onUpdateButtonClick,
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    ) {
                        when (updateState) {
                            UpdateState.Available -> {
                                Icon(
                                    modifier = Modifier.size(ButtonDefaults.IconSize),
                                    painter = painterResource(AppIcons.Download),
                                    contentDescription = null,
                                )
                            }
                            UpdateState.Downloading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(ButtonDefaults.IconSize),
                                    strokeWidth = ButtonDefaults.IconSize / 8,
                                )
                            }
                            UpdateState.ReadyToInstall -> {
                                Icon(
                                    modifier = Modifier.size(ButtonDefaults.IconSize),
                                    painter = painterResource(AppIcons.DownloadDone),
                                    contentDescription = null,
                                )
                            }
                            else -> {}
                        }
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            when (updateState) {
                                UpdateState.Available -> {
                                    stringResource(AppStrings.Button.UpdateAvailable)
                                }
                                UpdateState.Downloading -> {
                                    stringResource(AppStrings.Button.Downloading)
                                }
                                UpdateState.ReadyToInstall -> {
                                    stringResource(AppStrings.Button.ReadyToInstall)
                                }
                                else -> ""
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                } else if (showReviewButton) {
                    OutlinedButton(
                        modifier = Modifier.padding(horizontal = 4.dp).weight(1f),
                        onClick = onReviewButtonClick,
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    ) {
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(AppIcons.Reviews),
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(AppStrings.Button.RateTheApp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                FilledTonalIconButton(onClick = { onAboutClick() }) {
                    Icon(
                        painter = painterResource(id = AppIcons.Info),
                        contentDescription = null,
                    )
                }
                FilledIconButton(onClick = { onSettingsClick() }) {
                    Icon(
                        painter = painterResource(id = AppIcons.Settings),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun CustomNavDrawerItem(
    drawerItem: DrawerItem,
    currentDestination: NavDestination?,
) {
    val isSelected =
        currentDestination?.hierarchy
            ?.any {
                it.route == drawerItem.route ||
                    if ((it.route ?: "").contains("label")) {
                        (it.route ?: "").substringBefore("/") ==
                            drawerItem.route.substringBefore("/")
                    } else {
                        false
                    }
            } ?: false

    NavigationDrawerItem(
        icon = {
            Icon(
                painter = drawerItem.icon.asPainter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        },
        badge =
            if (drawerItem.badge !is UiText.Empty) {
                {
                    Text(
                        text = drawerItem.badge.asString(),
                    )
                }
            } else {
                null
            },
        label = {
            Text(
                text = drawerItem.title.asString(),
                style = MaterialTheme.typography.labelLarge,
            )
        },
        selected = isSelected,
        onClick = { drawerItem.onClick(drawerItem) },
        modifier =
            Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}
