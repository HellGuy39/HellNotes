package com.hellguy39.hellnotes.feature.labeledit.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.ui.focus.requestOnceAfterRecompositionIf
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.labeledit.LabelEditUiState

@Composable
fun LabelEditScreenContent(
    paddingValues: PaddingValues,
    uiState: LabelEditUiState,
    onCreateLabel: (name: String) -> Boolean,
    onLabelUpdated: (index: Int, name: String) -> Unit,
    onDeleteLabel: (index: Int) -> Unit,
    focusRequester: FocusRequester,
) {
    val context = LocalContext.current

    focusRequester.requestOnceAfterRecompositionIf {
        uiState.action == context.getString(AppStrings.Action.Create)
    }

    val itemModifier =
        remember {
            Modifier
                .fillMaxWidth()
                .padding(Spaces.extraSmall)
        }

    LazyColumn(
        contentPadding = paddingValues,
    ) {
        item(key = -1) {
            LabelSearchItem(
                modifier = itemModifier,
                focusRequester = focusRequester,
                onCreateLabel = onCreateLabel,
            )
        }
        itemsIndexed(
            items = uiState.labels,
            key = { index, label -> label.id ?: 0 },
        ) { index, label ->
            LabelItem(
                modifier = itemModifier,
                label = label,
                onDelete = { onDeleteLabel(index) },
                onLabelChange = { text ->
                    onLabelUpdated(index, text)
                },
            )
        }
    }
}
