package com.hellguy39.hellnotes.feature.note_detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.isNoteValid
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.components.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    snackbarHost: @Composable () -> Unit,
    noteDetailContentSelection: NoteDetailContentSelection,
    noteDetailChecklistSelection: NoteDetailChecklistSelection,
    dropdownMenuSelection: NoteDetailDropdownMenuSelection,
    uiState: NoteDetailUiState,
    topAppBarSelection: NoteDetailTopAppBarSelection,
    bottomBarSelection: NoteDetailBottomBarSelection
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = uiState.isLoading) {
        if (!uiState.note.isNoteValid() && !uiState.isLoading) {
            focusRequester.requestFocus()
        }
    }

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            NoteDetailContent(
                innerPadding = innerPadding,
                selection = noteDetailContentSelection,
                checklistSelection = noteDetailChecklistSelection,
                focusRequester = focusRequester,
                uiState = uiState,
                lazyListState = lazyListState
            )
        },
        topBar = {
            NoteDetailTopAppBar(
                scrollBehavior = scrollBehavior,
                topAppBarSelection = topAppBarSelection,
                dropdownMenuSelection = dropdownMenuSelection
            )
        },
        bottomBar = {
            val isAtBottom = !lazyListState.canScrollForward
            val fraction = if (isAtBottom) 1f else 0f
            val appBarContainerColor by animateColorAsState(
                targetValue = lerp(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level2),
                    MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level0),
                    FastOutLinearInEasing.transform(fraction)
                ),
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appBarContainerColor)
            ) {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = { bottomBarSelection.onLabels() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Label),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Labels)
                        )
                    }

                    IconButton(
                        onClick = { bottomBarSelection.onReminder() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Notifications),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Reminder)
                        )
                    }

                    IconButton(
                        onClick = { bottomBarSelection.onChecklist() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Checklist),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Text.Edited,
                            formatArgs = arrayOf(
                                DateTimeUtils.formatBest(uiState.note.editedAt)
                            )
                        ),
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )

                }
            }
        },
        snackbarHost = snackbarHost,
    )
}

data class NoteDetailBottomBarSelection(
    val onReminder: () -> Unit,
    val onLabels: () -> Unit,
    val onChecklist: () -> Unit
)