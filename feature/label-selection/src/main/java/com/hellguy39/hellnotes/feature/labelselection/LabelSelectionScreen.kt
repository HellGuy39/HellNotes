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
package com.hellguy39.hellnotes.feature.labelselection

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.components.items.HNCheckboxListItem
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.IconSize
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelSelectionScreen(
    uiState: LabelSelectionUiState,
    onNavigationBack: () -> Unit,
    onSearchUpdate: (String) -> Unit,
    onCreateNewLabelClick: () -> Unit,
    onToggleLabelCheckbox: (index: Int) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            HNTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                content = {
                    HNClearTextField(
                        value = uiState.search,
                        hint = stringResource(id = AppStrings.Hint.Label),
                        onValueChange = { newText -> onSearchUpdate(newText) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge,
                    )
                },
            )
        },
        content = { paddingValues ->
            LabelSelectionContent(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
                isEmpty = uiState.isEmpty,
                isShowCreateNewLabel = uiState.isShowCreateNewLabelItem,
                checkableLabels = uiState.checkableLabels,
                onCreateNewLabelClick = onCreateNewLabelClick,
                onToggleLabelCheckbox = onToggleLabelCheckbox,
            )
        },
    )
}

@Composable
fun LabelSelectionContent(
    modifier: Modifier,
    contentPadding: PaddingValues,
    isEmpty: Boolean,
    isShowCreateNewLabel: Boolean,
    checkableLabels: SnapshotStateList<CheckableLabel>,
    onCreateNewLabelClick: () -> Unit,
    onToggleLabelCheckbox: (index: Int) -> Unit,
) {
    if (isEmpty) {
        EmptyContentPlaceholder(
            modifier = modifier,
            heroIcon = UiIcon.DrawableResources(AppIcons.Label),
            message = UiText.StringResources(AppStrings.Placeholder.LabelSelection),
        )
    } else {
        LazyColumn(
            contentPadding = contentPadding,
            modifier = modifier,
        ) {
            itemsIndexed(
                items = checkableLabels,
                key = { index, checkableLabel -> checkableLabel.label.id ?: 0 },
                contentType = { index, checkableLabel -> checkableLabel },
            ) { index, checkableLabel ->
                HNCheckboxListItem(
                    onClick = {
                        onToggleLabelCheckbox(index)
                    },
                    leadingContent = {
                        Icon(
                            modifier = Modifier.size(IconSize.medium),
                            painter = UiIcon.DrawableResources(AppIcons.Label).asPainter(),
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text(UiText.DynamicString(checkableLabel.label.name).asString())
                    },
                    checked = checkableLabel.isChecked,
                )
            }
            if (isShowCreateNewLabel) {
                item(key = -1) {
                    HNListItem(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(Spaces.medium),
                        heroIcon = UiIcon.DrawableResources(AppIcons.NewLabel),
                        title = UiText.StringResources(AppStrings.MenuItem.CreateNewLabel),
                        onClick = onCreateNewLabelClick,
                        iconSize = IconSize.medium,
                    )
                }
            }
        }
    }
}
