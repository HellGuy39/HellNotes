package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.NoteSwipe
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeScreenMultiActionSelection
import com.hellguy39.hellnotes.feature.home.HomeScreenVisualsSelection
import com.hellguy39.hellnotes.feature.home.label.components.LabelDropdownMenuSelection
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBar
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LabelScreen(
    navController: NavController,
    labelViewModel: LabelViewModel = hiltViewModel(),
    visualsSelection: HomeScreenVisualsSelection,
    multiActionSelection: HomeScreenMultiActionSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val uiState by labelViewModel.uiState.collectAsStateWithLifecycle()

    val deleteDialogState = rememberDialogState()
    val renameDialogState = rememberDialogState()

    CustomDialog(
        state = renameDialogState,
        showContentDividers = false,
        title = "Rename label",
        content = {
            val focusRequester = remember { FocusRequester() }

            LaunchedEffect(key1 = Unit) {
                focusRequester.requestFocus()
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var name by remember {
                    mutableStateOf(
                        TextFieldValue(
                            text = uiState.label.name,
                            selection = TextRange(uiState.label.name.length)
                        )
                    )
                }

                var errorMessage by remember { mutableStateOf("") }
                var isError by rememberSaveable { mutableStateOf(false) }

                fun validate() {
                    if (name.text.isEmpty() || name.text.isBlank()) {
                        errorMessage = context.getString(HellNotesStrings.Text.LabelCannotBeEmpty)
                        isError = true
                        return
                    }
                    if (labelViewModel.isLabelUnique(name.text)) {
                        labelViewModel.send(LabelUiEvent.RenameLabel(name.text))
                        renameDialogState.dismiss()
                    } else {
                        errorMessage = context.getString(HellNotesStrings.Text.LabelAlreadyExist)
                        isError = true
                    }
                }

                Column {
                    OutlinedTextField(
                        modifier = Modifier
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
                                text = stringResource(id = HellNotesStrings.Hint.Label)
                            )
                        },
                        trailingIcon = {
                            if (isError) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Error),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        supportingText = {
                            if (isError) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = errorMessage,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp, alignment = Alignment.End
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            renameDialogState.dismiss()
                        },
                    ) {
                        Text(
                            text = stringResource(id = HellNotesStrings.Button.Cancel),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {
                            validate()
                        },
                    ) {
                        Text(
                            text = stringResource(id = HellNotesStrings.Button.Rename),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    )

    CustomDialog(
        state = deleteDialogState,
        heroIcon = painterResource(id = HellNotesIcons.Delete),
        title = stringResource(id = HellNotesStrings.Title.DeleteThisLabel),
        message = stringResource(id = HellNotesStrings.Helper.DeleteLabelDialog),
        onCancel = {
            deleteDialogState.dismiss()
        },
        onAccept = {
            labelViewModel.send(LabelUiEvent.DeleteLabel)
            visualsSelection.resetDrawerRoute()
            deleteDialogState.dismiss()
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            AnimatedContent(targetState = visualsSelection.listStyle) { listStyle ->

                if (uiState.notes.isEmpty()) {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Label),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }

                NoteList(
                    innerPadding = paddingValues,
                    noteSelection = NoteSelection(
                        noteStyle = visualsSelection.noteStyle,
                        onClick = { note ->
                            if (multiActionSelection.selectedNotes.isEmpty()) {
                                navController.navigateToNoteDetail(note.id)
                            } else {
                                if (multiActionSelection.selectedNotes.contains(note)) {
                                    multiActionSelection.onUnselectNote(note)
                                } else {
                                    multiActionSelection.onSelectNote(note)
                                }
                            }
                        },
                        onLongClick = { note ->
                            if (multiActionSelection.selectedNotes.contains(note)) {
                                multiActionSelection.onUnselectNote(note)
                            } else {
                                multiActionSelection.onSelectNote(note)
                            }
                        },
                        onDismiss = { direction, note ->
                            val swipeAction = if (direction == DismissDirection.StartToEnd)
                                visualsSelection.noteSwipesState.swipeRight
                            else
                                visualsSelection.noteSwipesState.swipeLeft

                            when(swipeAction) {
                                NoteSwipe.None -> false
                                NoteSwipe.Delete -> {
                                    multiActionSelection.onDeleteNote(note)
                                    true
                                }
                                NoteSwipe.Archive -> {
                                    multiActionSelection.onArchiveNote(note, true)
                                    true
                                }
                            }
                        },
                        isSwipeable = visualsSelection.noteSwipesState.enabled
                    ),
                    categories = listOf(
                        NoteCategory(
                            notes = uiState.notes
                        )
                    ),
                    selectedNotes = multiActionSelection.selectedNotes,
                    listStyle = listStyle,
                )
            }
        },
        topBar = {
            LabelTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = LabelTopAppBarSelection(
                    selectedNotes = multiActionSelection.selectedNotes,
                    onDeleteSelected = multiActionSelection.onDeleteSelectedNotes,
                    onCancelSelection = multiActionSelection.onCancelSelection,
                    onArchiveSelected = { multiActionSelection.onArchiveSelectedNotes(true) },
                    onNavigation = {
                        scope.launch {
                            visualsSelection.drawerState.open()
                        }
                    },
                    listStyle = visualsSelection.listStyle,
                    onSearch = navController::navigateToSearch,
                    onChangeListStyle = visualsSelection.onUpdateListStyle
                ),
                dropdownMenuSelection = LabelDropdownMenuSelection(
                    onRename = {
                        renameDialogState.show()
                    },
                    onDelete = {
                        deleteDialogState.show()
                    }
                ),
                label = uiState.label
            )
        }
    )
}