package com.hellguy39.hellnotes.feature.labeledit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.labeledit.components.LabelEditScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelEditScreen(
    uiState: LabelEditUiState,
    onNavigationButtonClick: () -> Unit,
    onCreateLabel: (name: String) -> Boolean,
    onLabelUpdated: (index: Int, name: String) -> Unit,
    onDeleteLabel: (index: Int) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
                    onCreateLabel = onCreateLabel,
                    onLabelUpdated = onLabelUpdated,
                    onDeleteLabel = onDeleteLabel,
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
