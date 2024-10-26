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
package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    labelViewModel: LabelViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "LabelScreen")

    val context = LocalContext.current

    val uiState by labelViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

    labelViewModel.singleUiEventFlow.collectAsEventsWithLifecycle { event ->
        when (event) {
            is LabelSingleUiEvent.ShowSnackbar -> {
                homeState.showSnack(event.text, event.action)
            }
        }
    }

    labelViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is LabelNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    val deleteDialogState = rememberDialogState()
    val renameDialogState = rememberDialogState()

    CustomDialog(
        state = renameDialogState,
        showContentDividers = false,
        title = stringResource(id = AppStrings.Title.RenameLabel),
        content = {
            val focusRequester = remember { FocusRequester() }

            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                var name by remember {
                    mutableStateOf(
                        TextFieldValue(
                            text = uiState.label.name,
                            selection = TextRange(uiState.label.name.length),
                        ),
                    )
                }

                var errorMessage by remember { mutableStateOf("") }
                var isError by rememberSaveable { mutableStateOf(false) }

                fun validate() {
                    if (name.text.isEmpty() || name.text.isBlank()) {
                        errorMessage = context.getString(AppStrings.Snack.LabelCannotBeEmpty)
                        isError = true
                        return
                    }
                    if (labelViewModel.isLabelUnique(name.text)) {
                        labelViewModel.send(LabelUiEvent.RenameLabel(name.text))
                        renameDialogState.dismiss()
                    } else {
                        errorMessage = context.getString(AppStrings.Snack.LabelAlreadyExist)
                        isError = true
                    }
                }

                Column {
                    OutlinedTextField(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .padding(horizontal = 16.dp),
                        value = name,
                        onValueChange = { newText ->
                            isError = false
                            name = newText
                        },
                        isError = isError,
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = stringResource(id = AppStrings.Hint.Label),
                            )
                        },
                        trailingIcon = {
                            if (isError) {
                                Icon(
                                    painter = painterResource(id = AppIcons.Error),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            }
                        },
                        supportingText = {
                            if (isError) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = errorMessage,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        },
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    horizontalArrangement =
                        Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.End,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(
                        onClick = {
                            renameDialogState.dismiss()
                        },
                    ) {
                        Text(
                            text = stringResource(id = AppStrings.Button.Cancel),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {
                            validate()
                        },
                    ) {
                        Text(
                            text = stringResource(id = AppStrings.Button.Rename),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        },
    )

    CustomDialog(
        state = deleteDialogState,
        heroIcon = painterResource(id = AppIcons.Delete),
        title = stringResource(id = AppStrings.Title.DeleteThisLabel),
        message = stringResource(id = AppStrings.Supporting.DeleteLabel),
        onCancel = {
            deleteDialogState.dismiss()
        },
        onAccept = {
            deleteDialogState.dismiss()
            homeState.resetNavigation()
            labelViewModel.send(LabelUiEvent.DeleteLabel)
        },
    )

    LabelScreen(
        uiState = uiState,
        visualState = visualState,
        onNoteClick = remember { { noteId -> labelViewModel.send(LabelUiEvent.NoteClick(noteId)) } },
        onNotePress = remember { { noteId -> labelViewModel.send(LabelUiEvent.NotePress(noteId)) } },
        onDismissNote =
            remember {
                { direction, noteId ->
                    val swipeAction = visualsViewModel.calculateSwipeAction(direction)
                    labelViewModel.send(LabelUiEvent.DismissNote(swipeAction, noteId))
                    visualsViewModel.calculateSwipeResult(swipeAction)
                }
            },
        onDeleteSelectedClick = remember { { labelViewModel.onDeleteSelectedItems() } },
        onCancelSelectionClick = remember { { labelViewModel.onCancelItemSelection() } },
        onArchiveSelectedClick = remember { { labelViewModel.onArchiveSelectedItems() } },
        onNavigationClick = remember { { homeState.openDrawer() } },
        listStyle = visualState.listStyle,
        onSearchClick = remember { { navigateToSearch() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        onRenameClick = remember { { renameDialogState.show() } },
        onDeleteClick = remember { { deleteDialogState.show() } },
        snackbarHostState = homeState.snackbarHostState,
    )
}
