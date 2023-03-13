package com.hellguy39.hellnotes.feature.label_edit

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.label_edit.components.LabelEditScreenContent
import com.hellguy39.hellnotes.feature.label_edit.components.LabelItemSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelEditScreen(
    uiState: LabelEditUiState,
    onNavigationButtonClick: () -> Unit,
    labelItemSelection: LabelItemSelection,
    snackbarHostState: SnackbarHostState
) {
    BackHandler(onBack = onNavigationButtonClick)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = uiState.isLoading) {
        if (!uiState.isLoading && uiState.action == context.getString(HellNotesStrings.Action.Create)) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            Crossfade(targetState = uiState.labels) { labels ->
                LabelEditScreenContent(
                    paddingValues = paddingValues,
                    labels = labels,
                    labelItemSelection = labelItemSelection,
                    focusRequester = focusRequester
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