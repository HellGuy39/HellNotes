package com.hellguy39.hellnotes.feature.labeledit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.labeledit.components.LabelEditScreenContent
import com.hellguy39.hellnotes.feature.labeledit.components.LabelEditScreenContentSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelEditScreen(
    uiState: LabelEditUiState,
    onNavigationButtonClick: () -> Unit,
    labelEditScreenContentSelection: LabelEditScreenContentSelection,
    snackbarHostState: SnackbarHostState,
) {
    BackHandler { onNavigationButtonClick() }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            if (!uiState.isIdle) {
                LabelEditScreenContent(
                    paddingValues = paddingValues,
                    uiState = uiState,
                    selection = labelEditScreenContentSelection,
                    focusRequester = focusRequester,
                )
            }
        },
        topBar = {
            HNTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.Labels),
            )
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
    )
}
