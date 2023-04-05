package com.hellguy39.hellnotes.feature.label_edit.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.feature.label_edit.LabelEditUiState

@Composable
fun LabelEditScreenContent(
    paddingValues: PaddingValues,
    uiState: LabelEditUiState.Success,
    selection: LabelEditScreenContentSelection,
    focusRequester: FocusRequester
) {
    LazyColumn(
        contentPadding = paddingValues
    ) {
        item(
            key = -1
        ) {
            LabelSearchItem(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                focusRequester = focusRequester,
                onCreateLabel = selection.onCreateLabel
            )
        }
        items(
            items = uiState.labels,
            key = { label -> label.id ?: 0}
        ) { label ->
            LabelItem(
                modifier = Modifier
                    .padding(4.dp),
                label = label,
                onDelete = { selection.onDeleteLabel(label) },
                onLabelChange = { text -> selection.onLabelUpdated(label.copy(name = text)) }
            )
        }
    }
}


data class LabelEditScreenContentSelection(
    val onCreateLabel: (Label) -> Boolean,
    val onLabelUpdated: (Label) -> Unit,
    val onDeleteLabel: (Label) -> Unit
)