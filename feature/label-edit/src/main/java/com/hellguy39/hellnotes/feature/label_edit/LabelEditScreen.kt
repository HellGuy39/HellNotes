package com.hellguy39.hellnotes.feature.label_edit

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.label_edit.components.LabelItemSelection
import com.hellguy39.hellnotes.feature.label_edit.components.LabelScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsScreen(
    labels: List<Label>,
    onNavigationButtonClick: () -> Unit,
    labelItemSelection: LabelItemSelection,
    snackbarHostState: SnackbarHostState
) {
    BackHandler(onBack = onNavigationButtonClick)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            Crossfade(targetState = labels) { labels ->
                LabelScreenContent(
                    paddingValues = paddingValues,
                    labels = labels,
                    labelItemSelection = labelItemSelection
                )
            }
        },
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Labels)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
}