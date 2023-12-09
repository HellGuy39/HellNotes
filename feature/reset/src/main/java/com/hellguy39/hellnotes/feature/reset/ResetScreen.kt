package com.hellguy39.hellnotes.feature.reset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.cards.InfoCard
import com.hellguy39.hellnotes.core.ui.components.items.HNCheckboxItem
import com.hellguy39.hellnotes.core.ui.components.items.HNListHeader
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
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
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Reset),
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier =
                        Modifier
                            .navigationBarsPadding()
                            .padding(Spaces.medium),
                    onClick = onResetClick,
                    enabled = uiState.resetButtonEnabled(),
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Reset),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding() + Spaces.medium,
                        bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                    ),
            verticalArrangement =
                Arrangement.spacedBy(
                    space = Spaces.large,
                    alignment = Alignment.CenterVertically,
                ),
        ) {
            InfoCard(
                modifier = Modifier.padding(horizontal = Spaces.medium),
                title = stringResource(id = HellNotesStrings.Title.Attention),
                body = stringResource(id = HellNotesStrings.Body.Reset),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
            ) {
                HNListHeader(
                    modifier =
                        Modifier
                            .padding(horizontal = Spaces.medium)
                            .padding(bottom = Spaces.small),
                    title = stringResource(id = HellNotesStrings.Subtitle.SelectActions),
                )

                HNCheckboxItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spaces.medium, vertical = Spaces.medium),
                    onClick = onToggleResetDatabase,
                    title = stringResource(id = HellNotesStrings.Checkbox.ClearDatabaseTitle),
                    subtitle = stringResource(id = HellNotesStrings.Checkbox.ClearDatabaseSubtitle),
                    checked = uiState.isResetDatabase,
                )

                HNCheckboxItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(Spaces.medium),
                    onClick = onToggleResetSettings,
                    title = stringResource(id = HellNotesStrings.Checkbox.ResetSettingsTitle),
                    subtitle = stringResource(id = HellNotesStrings.Checkbox.ResetSettingsSubtitle),
                    checked = uiState.isResetSettings,
                )
            }
        }
    }
}
