package com.hellguy39.hellnotes.feature.language_selection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.ui.components.items.HNRadioButtonItem
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.getDisplayName
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionScreen(
    onNavigationBack: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    uiState: LanguageSelectionUiState
) {
    BackHandler { onNavigationBack }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier,
                contentPadding = paddingValues,
            ) {
                item {
                    Column(
                        modifier = Modifier.selectableGroup(),
                    ) {
                        Language.languageCodes.forEach { code ->
                            HNRadioButtonItem(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(16.dp),
                                title = Language.from(code).getDisplayName(),
                                onClick = { onLanguageSelected(code) },
                                isSelected = code == uiState.languageCode,
                            )
                        }
                    }
                }
            }
        },
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                title = stringResource(id = HellNotesStrings.Title.Language)
            )
        }
    )
}