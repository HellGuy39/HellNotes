package com.hellguy39.hellnotes.feature.label_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.label_edit.components.LabelEditScreenContent
import com.hellguy39.hellnotes.feature.label_edit.components.LabelEditScreenContentSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelEditScreen(
    uiState: LabelEditUiState,
    onNavigationButtonClick: () -> Unit,
    labelEditScreenContentSelection: LabelEditScreenContentSelection,
    snackbarHostState: SnackbarHostState
) {
    BackHandler(onBack = onNavigationButtonClick)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = uiState is LabelEditUiState.Success) {
        uiState.let { state ->
            if (state is LabelEditUiState.Success) {
                if (state.action == context.getString(HellNotesStrings.Action.Create)) {
                    focusRequester.requestFocus()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            when(uiState) {
                is LabelEditUiState.Loading -> Unit
                is LabelEditUiState.Success -> {
                    LabelEditScreenContent(
                        paddingValues = paddingValues,
                        uiState = uiState,
                        selection = labelEditScreenContentSelection,
                        focusRequester = focusRequester
                    )
                }
            }
        },
        topBar = {
            HNTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Labels)
            )
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) }
    )
}