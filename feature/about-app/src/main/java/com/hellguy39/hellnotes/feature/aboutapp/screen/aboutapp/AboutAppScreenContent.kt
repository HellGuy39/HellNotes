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
package com.hellguy39.hellnotes.feature.aboutapp.screen.aboutapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.hellguy39.hellnotes.core.ui.components.items.HNNavigateListItem
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.aboutapp.BuildConfig

@Composable
fun AboutAppScreenContent(
    innerPadding: PaddingValues,
    onGithub: () -> Unit = {},
    onChangelog: () -> Unit = {},
    onPrivacyPolicy: () -> Unit = {},
    onTermsAndConditions: () -> Unit = {},
    onProvideFeedback: () -> Unit = {},
    onReset: () -> Unit = {},
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
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(Spaces.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
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

                    OutlinedIconButton(
                        onClick = onGithub,
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
                HNNavigateListItem(
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.Changelog))
                    },
                    onClick = onChangelog,
                )
                HNNavigateListItem(
                    onClick = onPrivacyPolicy,
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.PrivacyPolicy))
                    },
                )
                HNNavigateListItem(
                    onClick = onTermsAndConditions,
                    headlineContent = {
                        Text(
                            text =
                                stringResource(
                                    id = AppStrings.MenuItem.TermsAndConditions,
                                ),
                        )
                    },
                )
                HNNavigateListItem(
                    onClick = onProvideFeedback,
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.ProvideFeedback))
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
                HNNavigateListItem(
                    onClick = onReset,
                    headlineContent = {
                        Text(text = stringResource(id = AppStrings.MenuItem.Reset))
                    },
                )
            }
        }
    }
}
