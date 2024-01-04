package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel
import com.hellguy39.hellnotes.feature.home.label.components.LabelDropdownMenuSelection
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBar
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelScreen(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    labelViewModel: LabelViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "LabelScreen")

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val context = LocalContext.current

    val uiState by labelViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes by actionViewModel.selectedNotes.collectAsStateWithLifecycle()

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
            labelViewModel.send(LabelUiEvent.DeleteLabel)
            homeState.resetNavigation()
            deleteDialogState.dismiss()
        },
    )

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { CustomSnackbarHost(state = homeState.snackbarHostState) },
        content = { paddingValues ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.Folder),
                    message = UiText.StringResources(AppStrings.Placeholder.Empty),
                )
            } else {
                AnimatedContent(
                    targetState = visualState.listStyle,
                    label = "listStyle",
                ) { listStyle ->
                    NoteList(
                        innerPadding = paddingValues,
                        noteStyle = visualState.noteStyle,
                        onClick = { note ->
                            if (selectedNotes.isEmpty()) {
                                navigateToNoteDetail(note.id)
                            } else {
                                if (selectedNotes.contains(note)) {
                                    actionViewModel.unselectNote(note)
                                } else {
                                    actionViewModel.selectNote(note)
                                }
                            }
                        },
                        onLongClick = { note ->
                            if (selectedNotes.contains(note)) {
                                actionViewModel.unselectNote(note)
                            } else {
                                actionViewModel.selectNote(note)
                            }
                        },
                        onDismiss = { direction, note ->
                            val swipeAction =
                                if (direction == DismissDirection.StartToEnd) {
                                    visualState.noteSwipesState.swipeRight
                                } else {
                                    visualState.noteSwipesState.swipeLeft
                                }

                            when (swipeAction) {
                                NoteSwipe.None -> false
                                NoteSwipe.Delete -> {
                                    actionViewModel.deleteNote(note = note)
                                    true
                                }
                                NoteSwipe.Archive -> {
                                    actionViewModel.archiveNote(note = note, isArchived = true)
                                    true
                                }
                            }
                        },
                        isSwipeable = visualState.noteSwipesState.enabled,
                        categories =
                            listOf(
                                NoteCategory(
                                    notes = uiState.notes,
                                ),
                            ),
                        selectedNotes = selectedNotes,
                        listStyle = listStyle,
                    )
                }
            }
        },
        topBar = {
            LabelTopAppBar(
                scrollBehavior = scrollBehavior,
                selection =
                    LabelTopAppBarSelection(
                        selectedNotes = selectedNotes,
                        onDeleteSelected = actionViewModel::deleteSelectedNotes,
                        onCancelSelection = actionViewModel::cancelNoteSelection,
                        onArchiveSelected = { actionViewModel.archiveSelectedNotes(true) },
                        onNavigation = { homeState.openDrawer() },
                        listStyle = visualState.listStyle,
                        onSearch = { navigateToSearch() },
                        onChangeListStyle = visualsViewModel::toggleListStyle,
                    ),
                dropdownMenuSelection =
                    LabelDropdownMenuSelection(
                        onRename = {
                            renameDialogState.show()
                        },
                        onDelete = {
                            deleteDialogState.show()
                        },
                    ),
                label = uiState.label,
            )
        },
    )
}
