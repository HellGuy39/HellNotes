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
package com.hellguy39.hellnotes.feature.languageselection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.ui.asDisplayableString
import com.hellguy39.hellnotes.core.ui.components.items.HNRadioButtonItem
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionScreen(
    onNavigationButtonClick: () -> Unit,
    onLanguageClick: (language: Language) -> Unit,
    uiState: LanguageSelectionUiState,
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val languages by remember { mutableStateOf(Language.languages()) }

    Scaffold(
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.Language),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .selectableGroup(),
            contentPadding =
                PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                ),
        ) {
            items(
                items = languages,
                key = { language -> language.tag },
            ) { language ->
                HNRadioButtonItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(Spaces.medium),
                    title = language.asDisplayableString(),
                    onClick = { onLanguageClick(language) },
                    isSelected = language.tag == uiState.language.tag,
                )
            }
        }
    }
}
