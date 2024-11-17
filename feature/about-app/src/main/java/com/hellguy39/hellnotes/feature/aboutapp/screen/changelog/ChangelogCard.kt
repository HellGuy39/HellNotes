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
package com.hellguy39.hellnotes.feature.aboutapp.screen.changelog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun ChangelogCard(
    modifier: Modifier = Modifier,
    release: Release,
    onOpenReleaseClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier,
        onClick = { isExpanded = isExpanded.not() },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Spaces.medium),
            verticalArrangement = Arrangement.spacedBy(Spaces.medium),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(Spaces.small),
                ) {
                    Text(
                        text = release.tagName ?: "",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text =
                            DateTimeUtils.formatLocalDateTime(
                                DateTimeUtils.iso8061toLocalDateTime(
                                    release.publishedAt ?: "",
                                ),
                                DateTimeUtils.DATE_TIME_PATTERN,
                            ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                IconButton(onClick = { isExpanded = isExpanded.not() }) {
                    Icon(
                        painter = painterResource(id = AppIcons.expand(isExpanded)),
                        contentDescription = null,
                    )
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Spaces.medium),
                ) {
                    Divider()
                    Text(
                        text = release.body ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Divider()
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    onClick = onOpenReleaseClick,
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        painter = painterResource(id = AppIcons.ArrowOutward),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = AppStrings.Button.OpenARelease))
                }
            }
        }
    }
}
