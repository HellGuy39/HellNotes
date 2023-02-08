package com.hellguy39.hellnotes.feature.home.trash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.trash.components.TrashDropdownMenuSelection
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBar
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    uiState: TrashUiState,
    listStyle: ListStyle,
    trashTopAppBarSelection: TrashTopAppBarSelection,
    noteSelection: NoteSelection,
    trashDropdownMenuSelection: TrashDropdownMenuSelection,
    categories: List<NoteCategory>
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrashTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = trashTopAppBarSelection,
                trashDropdownMenuSelection = trashDropdownMenuSelection
            )
        },
        content = { paddingValues ->

            if (uiState.trashNotes.isEmpty()) {
                EmptyContentPlaceholder(
                    heroIcon = painterResource(id = HellNotesIcons.Delete),
                    message = stringResource(id = HellNotesStrings.Text.NoNotesInTrash)
                )
                return@Scaffold
            }

            when (listStyle) {
                ListStyle.Column -> {
                    NoteColumnList(
                        innerPadding = paddingValues,
                        noteSelection = noteSelection,
                        categories = categories,
                        selectedNotes = uiState.selectedNotes,
                        listHeader = {
                            TipCard(
                                isVisible = true,
                                message = "Notes in the trash are deleted automatically after 7 days",
                                onClose = {}
                            )
                        }
                    )
                }
                ListStyle.Grid -> {
                    NoteGridList(
                        innerPadding = paddingValues,
                        noteSelection = noteSelection,
                        categories = categories,
                        selectedNotes = uiState.selectedNotes,
                        listHeader = {
                            TipCard(
                                isVisible = true,
                                message = "Notes in the trash are deleted automatically after 7 days",
                                onClose = {}
                            )
                        }
                    )
                }
            }

        }
    )

}