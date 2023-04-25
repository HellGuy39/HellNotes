package com.hellguy39.hellnotes.feature.label_selection

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.components.items.HNCheckboxItem
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelSelectionScreen(
    onNavigationBack: () -> Unit,
    uiState: LabelSelectionUiState,
    selection: LabelSelectionScreenSelection
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)

    val listItemModifier = Modifier
        .padding(horizontal = 16.dp, vertical = 16.dp)
        .fillMaxWidth()

    Scaffold(
        topBar = {
            HNTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                content = {
                    HNClearTextField(
                        value = uiState.search,
                        hint = stringResource(id = HellNotesStrings.Hint.Label),
                        onValueChange = { newText -> selection.onSearchUpdate(newText) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        },
        content = { paddingValues ->

            if(uiState.isLoading) {
                return@Scaffold
            }

            if (uiState.labels.isEmpty() && uiState.search.isEmpty()) {
                EmptyContentPlaceholder(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(paddingValues)
                        .fillMaxSize(),
                    heroIcon = painterResource(id = HellNotesIcons.Label),
                    message = stringResource(id = HellNotesStrings.Helper.LabelSelectionPlaceholder)
                )
                return@Scaffold
            }

            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = uiState.labels,
                    key = { label -> label.id ?: 0 }
                ) { label ->
                    val isChecked = label.noteIds.contains(uiState.noteId)
                    HNCheckboxItem(
                        modifier = listItemModifier,
                        onClick = { selection.onLabelSelectedUpdate(label, !isChecked) },
                        heroIcon = painterResource(id = HellNotesIcons.Label),
                        title = label.name,
                        checked = isChecked,
                        iconSize = 24.dp
                    )
                }
                if (isShowCreateNewLabelItem(uiState.labels, uiState.search)) {
                    item(
                        key = -1
                    ) {
                        HNListItem(
                            modifier = listItemModifier,
                            heroIcon = painterResource(id = HellNotesIcons.NewLabel),
                            title = stringResource(id = HellNotesStrings.MenuItem.CreateNewLabel),
                            onClick = selection.onCreateNewLabel,
                            iconSize = 24.dp
                        )
                    }
                }
            }

        }
    )
}

data class LabelSelectionScreenSelection(
    val onCreateNewLabel: () -> Unit,
    val onSearchUpdate: (String) -> Unit,
    val onLabelSelectedUpdate: (Label, Boolean) -> Unit
)

private fun isShowCreateNewLabelItem(allLabels: List<Label>, query: String): Boolean {
    return (allLabels.isEmpty() ||
            allLabels.size > 2 ||
            (allLabels.size == 1 && query != allLabels[0].name)) &&
            (query.isNotBlank() && query.isNotEmpty())
}