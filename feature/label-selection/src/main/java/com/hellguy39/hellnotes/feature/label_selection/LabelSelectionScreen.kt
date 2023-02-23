package com.hellguy39.hellnotes.feature.label_selection

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.components.items.SelectionCheckItem
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItem
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomTopAppBar
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

    Scaffold(
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                content = {
                    CustomTextField(
                        value = uiState.search,
                        hint = stringResource(id = HellNotesStrings.Hint.Search),
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

            Crossfade(targetState = uiState) { uiState ->
                if (uiState.labels.isEmpty() && uiState.search.isEmpty()) {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Label),
                        message = "Your labels will be displayed here"
                    )
                    return@Crossfade
                }

                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.labels) { label ->
                        SelectionCheckItem(
                            heroIcon = painterResource(id = HellNotesIcons.Label),
                            title = label.name,
                            checked = uiState.note.labelIds.contains(label.id),
                            onCheckedChange = { checked ->
                                selection.onLabelSelectedUpdate(label, checked)
                            }
                        )
                    }
                    if (isShowCreateNewLabelItem(uiState.labels, uiState.search)) {
                        item {
                            SelectionItem(
                                heroIcon = painterResource(id = HellNotesIcons.NewLabel),
                                title = stringResource(id = HellNotesStrings.MenuItem.CreateNewLabel),
                                onClick = { selection.onCreateNewLabel() }
                            )
                        }
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