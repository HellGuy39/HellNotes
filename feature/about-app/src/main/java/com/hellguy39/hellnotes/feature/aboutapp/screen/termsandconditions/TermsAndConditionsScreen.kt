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
package com.hellguy39.hellnotes.feature.aboutapp.screen.termsandconditions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.aboutapp.components.ScrollableRichText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TermsAndConditionsScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: TermsAndConditionsUiState,
    onTryAgain: () -> Unit,
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                HNTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onNavigationButtonClick = onNavigationButtonClick,
                    title = stringResource(id = AppStrings.Title.TermsAndConditions),
                    actions = {
                        if (!uiState.isLoading && !uiState.isError) {
                            FilterChip(
                                modifier =
                                    Modifier
                                        .height(FilterChipDefaults.Height)
                                        .padding(horizontal = 12.dp),
                                selected = false,
                                onClick = {},
                                label = { Text(text = stringResource(id = AppStrings.Chip.Markdown)) },
                            )
                        }
                    },
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        content = { paddingValues ->
            if (!uiState.isLoading && !uiState.isError) {
                ScrollableRichText(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues,
                    content = uiState.termsAndConditions,
                )
            } else if (uiState.isError) {
                EmptyContentPlaceholder(
                    modifier =
                        Modifier
                            .padding(paddingValues)
                            .padding(horizontal = 32.dp)
                            .fillMaxSize(),
                    heroIcon = painterResource(id = AppIcons.Error),
                    message = stringResource(id = AppStrings.Placeholder.FailedToLoadData),
                    actions = {
                        TextButton(
                            onClick = onTryAgain,
                        ) {
                            Text(text = stringResource(id = AppStrings.Button.TryAgain))
                        }
                    },
                )
            }
        },
    )
}
