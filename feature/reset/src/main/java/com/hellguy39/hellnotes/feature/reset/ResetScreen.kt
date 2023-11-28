package com.hellguy39.hellnotes.feature.reset

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.items.HNCheckboxItem
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.IconSize
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetScreen(
    uiState: ResetUiState,
    onNavigationButtonClick: () -> Unit,
    onResetClick: () -> Unit,
    onToggleResetDatabase: () -> Unit,
    onToggleResetSettings: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Reset)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(Spaces.medium),
                    onClick = onResetClick,
                    enabled = uiState.resetButtonEnabled()
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Reset),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(
                space = Spaces.medium,
                alignment = Alignment.CenterVertically
            )
        ) {
//            Image(
//                modifier = Modifier.size(IconSize.displayable),
//                painter = painterResource(id = HellNotesIcons.RestartAlt),
//                contentDescription = null,
//                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
//            )

            ElevatedCard(
                modifier = Modifier.padding(horizontal = Spaces.medium)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spaces.medium),
                    horizontalArrangement = Arrangement.spacedBy(Spaces.medium)
                ) {
                    // TODO: set accented icon tint & maybe create separate composable item
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Info),
                        contentDescription = null
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Spaces.small)
                    ) {
                        // TODO: replace with resource strings
                        Text(
                            text = "Attention",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = "This action will not be undone",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spaces.medium),
                text = stringResource(id = HellNotesStrings.Subtitle.SelectActions),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Start
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                // TODO: do title + body checkbox items
                HNCheckboxItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium, vertical = Spaces.medium),
                    onClick = onToggleResetDatabase,
                    checked = uiState.isResetDatabase,
                    title = stringResource(id = HellNotesStrings.Checkbox.ClearDatabase)
                )

                Divider(modifier = Modifier.padding(horizontal = Spaces.medium))

                HNCheckboxItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium, vertical = Spaces.medium),
                    onClick = onToggleResetSettings,
                    title = stringResource(id = HellNotesStrings.Checkbox.ResetSettings),
                    checked = uiState.isResetSettings,
                )
            }
        }
    }
}