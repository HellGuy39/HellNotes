package com.hellguy39.hellnotes.feature.home.list.labels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.local.database.Label
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.list.labels.components.LabelList
import com.hellguy39.hellnotes.feature.home.list.labels.components.LabelsTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsScreen(
    uiState: LabelsUiState,
    onLabelClick: (Label) -> Unit,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LabelsTopAppBar(
                scrollBehavior = scrollBehavior,
            )
        },
        content = { paddingValues ->
            when(uiState) {
                is LabelsUiState.Idle -> Unit
                is LabelsUiState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Label),
                        message = stringResource(id = HellNotesStrings.Placeholder.Empty)
                    )
                }
                is LabelsUiState.Success -> {
                    LabelList(
                        modifier = Modifier.fillMaxSize(),
                        paddingValues = paddingValues,
                        items = uiState.labels,
                        onClick = onLabelClick
                    )
                }
            }
        },
    )
}