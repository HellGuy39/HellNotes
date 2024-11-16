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
package com.hellguy39.hellnotes.feature.aboutapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.aboutapp.AboutAppScreenSelection
import com.hellguy39.hellnotes.feature.aboutapp.BuildConfig

@Composable
fun AboutAppScreenContent(
    innerPadding: PaddingValues,
    selection: AboutAppScreenSelection,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spaces.medium),
        contentPadding =
            PaddingValues(
                top = innerPadding.calculateTopPadding() + Spaces.medium,
                bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
            ),
    ) {
        item {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
                text = "Version ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
            )
        }
        item {
            ElevatedCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
            ) {
                Row(
                    modifier = Modifier.padding(Spaces.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spaces.medium),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spaces.extraSmall),
                    ) {
                        Text(
                            text = "Developed and designed by",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = "Aleksey Gadzhiev",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    OutlinedIconButton(
                        onClick = selection.onGithub,
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.GitHub),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        item {
            ElevatedCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
            ) {
                ListItem(
                    modifier = Modifier.clickable { selection.onChangelog() },
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.Changelog))
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(AppIcons.ChevronRight),
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    modifier = Modifier.clickable { selection.onPrivacyPolicy() },
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.PrivacyPolicy))
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(AppIcons.ChevronRight),
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    modifier = Modifier.clickable { selection.onTermsAndConditions() },
                    headlineContent = {
                        Text(
                            text =
                                stringResource(
                                    id = AppStrings.MenuItem.TermsAndConditions,
                                ),
                        )
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(AppIcons.ChevronRight),
                            contentDescription = null,
                        )
                    },
                )
                ListItem(
                    modifier = Modifier.clickable { selection.onProvideFeedback() },
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.ProvideFeedback))
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(AppIcons.ChevronRight),
                            contentDescription = null,
                        )
                    },
                )
            }
        }
        item {
            ElevatedCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
            ) {
                ListItem(
                    modifier = Modifier.clickable { selection.onReset() },
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.Reset))
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(AppIcons.ChevronRight),
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }
}
