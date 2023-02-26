package com.hellguy39.hellnotes.feature.language_selection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.Language
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.CustomRadioButton
import com.hellguy39.hellnotes.core.ui.components.items.SelectionIconItem
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.getDisplayName
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionScreen(
    onNavigationBack: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    uiState: LanguageSelectionUiState
) {
    BackHandler(onBack = onNavigationBack)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
            ) {
                items(Language.languageCodes) { code ->
                    val isSelected = code == uiState.languageCode
                    CustomRadioButton(
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = isSelected,
                                onClick = { onLanguageSelected(code) },
                                role = Role.RadioButton
                            ),
                        title = Language.from(code).getDisplayName(),
                        isSelected = isSelected
                    )
                }
            }
        },
        topBar = {
            CustomLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                title = stringResource(id = HellNotesStrings.Title.Language)
            )
        }
    )
}