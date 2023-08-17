package com.hellguy39.hellnotes.core.ui.component.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.util.ContentGroup

@Composable
fun HNNotesList(
    modifier: Modifier = Modifier,
    openedNoteId: Long = Note.EMPTY_ID,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    listStyle: ListStyle = ListStyle.Column,
    pinnedNoteWrappers: List<NoteWrapper> = listOf(),
    unpinnedNoteWrappers: List<NoteWrapper> = listOf(),
    showNameIfSingleGroup: Boolean = false,
    selectedNoteWrappers: List<NoteWrapper> = listOf(),
    lazyListState: LazyListState = rememberLazyListState(),
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    val contentGroups = listOf(
        ContentGroup(
            name = stringResource(id = HellNotesStrings.Label.Pinned),
            content = pinnedNoteWrappers,
        ),
        ContentGroup(
            name = stringResource(id = HellNotesStrings.Label.Others),
            content = unpinnedNoteWrappers,
        ),
    )

    NotesList(
        modifier = modifier,
        innerPadding = innerPadding,
        noteSelection = noteSelection,
        listStyle = listStyle,
        openedNoteId = openedNoteId,
        showNameIfSingleGroup = showNameIfSingleGroup,
        contentGroups = contentGroups,
        selectedNoteWrappers = selectedNoteWrappers,
        lazyListState = lazyListState,
        lazyStaggeredGridState = lazyStaggeredGridState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNNotesList(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection = NoteSelection(),
    openedNoteId: Long = Note.EMPTY_ID,
    listStyle: ListStyle = ListStyle.Column,
    groupName: String = "",
    showNameIfSingleGroup: Boolean = false,
    noteWrappers: List<NoteWrapper> = listOf(),
    selectedNoteWrappers: List<NoteWrapper> = listOf(),
    lazyListState: LazyListState = rememberLazyListState(),
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    val contentGroups = listOf(
        ContentGroup(
            name = groupName,
            content = noteWrappers
        )
    )

    NotesList(
        modifier = modifier,
        innerPadding = innerPadding,
        noteSelection = noteSelection,
        openedNoteId = openedNoteId,
        listStyle = listStyle,
        contentGroups = contentGroups,
        showNameIfSingleGroup = showNameIfSingleGroup,
        selectedNoteWrappers = selectedNoteWrappers,
        lazyListState = lazyListState,
        lazyStaggeredGridState = lazyStaggeredGridState,
    )
}

@Composable
internal fun NotesList(
    modifier: Modifier,
    innerPadding: PaddingValues,
    openedNoteId: Long,
    noteSelection: NoteSelection,
    showNameIfSingleGroup: Boolean,
    contentGroups: List<ContentGroup<NoteWrapper>>,
    listStyle: ListStyle,
    selectedNoteWrappers: List<NoteWrapper>,
    lazyListState: LazyListState,
    lazyStaggeredGridState: LazyStaggeredGridState
) {
    val listModifier = modifier
        .then(Modifier.testTag("item_list"))

    AnimatedContent(targetState = listStyle, label = "label") { style ->
        when(style) {
            ListStyle.Column -> {
                NoteColumnList(
                    modifier = listModifier,
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    openedNoteId = openedNoteId,
                    contentGroups = contentGroups,
                    selectedNoteWrappers = selectedNoteWrappers,
                    lazyListState = lazyListState,
                    showNameIfSingleGroup = showNameIfSingleGroup
                )
            }
            ListStyle.Grid -> {
                NoteGridList(
                    modifier = listModifier,
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    openedNoteId = openedNoteId,
                    contentGroups = contentGroups,
                    selectedNoteWrappers = selectedNoteWrappers,
                    lazyListState = lazyStaggeredGridState,
                    showNameIfSingleGroup = showNameIfSingleGroup
                )
            }
        }
    }
}