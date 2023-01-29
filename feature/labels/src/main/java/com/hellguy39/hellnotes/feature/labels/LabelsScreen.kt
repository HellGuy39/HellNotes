package com.hellguy39.hellnotes.feature.labels

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.labels.components.LabelItemSelection
import com.hellguy39.hellnotes.feature.labels.components.LabelScreenContent
import com.hellguy39.hellnotes.feature.labels.components.LabelsTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsScreen(
    labels: List<Label>,
    onNavigationButtonClick: () -> Unit,
    labelItemSelection: LabelItemSelection
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
            LabelsTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick
            )
        }
    )
}