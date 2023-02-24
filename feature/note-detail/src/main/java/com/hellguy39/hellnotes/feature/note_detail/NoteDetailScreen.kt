package com.hellguy39.hellnotes.feature.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.isNoteValid
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.components.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    snackbarHostState: SnackbarHostState,
    noteDetailContentSelection: NoteDetailContentSelection,
    dropdownMenuSelection: NoteDetailDropdownMenuSelection,
    uiState: NoteDetailUiState,
    topAppBarSelection: NoteDetailTopAppBarSelection,
    bottomBarSelection: NoteDetailBottomBarSelection
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = uiState.isLoading) {
        uiState.let {
            if (!uiState.note.isNoteValid() && !uiState.isLoading) {
                focusRequester.requestFocus()
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            NoteDetailContent(
                innerPadding = innerPadding,
                selection = noteDetailContentSelection,
                focusRequester = focusRequester,
                uiState = uiState
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

//            val colorTransitionFraction = scrollBehavior.state.overlappedFraction
//            //val fraction = if (colorTransitionFraction > 0.99f) 1f else 0f
//            val fraction = if (colorTransitionFraction > 0.01f) 0f else 1f
//            val appBarContainerColor by animateColorAsState(
//                targetValue = lerp(
//                    MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
//                    MaterialTheme.colorScheme.surface,
//                    FastOutLinearInEasing.transform(fraction)
//                ),
//                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
//            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            ) {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { bottomBarSelection.onLabels() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Label),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Labels)
                        )
                    }

                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Text.Edited,
                            formatArgs = arrayOf(
                                DateTimeUtils.formatBest(uiState.note.editedAt)
                            )
                        ),
                        modifier = Modifier,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = { bottomBarSelection.onReminder() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Notifications),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Reminder)
                        )
                    }
                }
            }

        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    )
}

data class NoteDetailBottomBarSelection(
    val onReminder: () -> Unit,
    val onLabels: () -> Unit,
)