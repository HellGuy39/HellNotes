package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    categories: List<NoteCategory> = listOf(),
    listStyle: ListStyle = ListStyle.Column,
    selectedNotes: List<NoteWrapper> = listOf(),
    listHeader: @Composable () -> Unit = {},
    lazyListState: LazyListState = rememberLazyListState(),
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    val listModifier = modifier
        .then(
            Modifier.testTag("item_list")
        )

    AnimatedContent(targetState = listStyle) { style ->
        when(style) {
            ListStyle.Column -> {
                NoteColumnList(
                    modifier = listModifier,
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    categories = categories,
                    selectedNotes = selectedNotes,
                    listHeader = listHeader,
                    lazyListState = lazyListState
                )
            }
            ListStyle.Grid -> {
                NoteGridList(
                    modifier = listModifier,
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    categories = categories,
                    selectedNotes = selectedNotes,
                    listHeader = listHeader,
                    lazyListState = lazyStaggeredGridState
                )
            }
        }
    }
}