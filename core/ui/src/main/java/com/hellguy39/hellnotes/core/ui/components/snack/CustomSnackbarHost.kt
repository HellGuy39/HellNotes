package com.hellguy39.hellnotes.core.ui.components.snack

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSnackbarHost(
    state: SnackbarHostState,
) {
    SnackbarHost(hostState = state) { data ->
        DismissableSnackbar(
            dismissState = rememberSnackbarDismissState(snackbarHostState = state),
            snackbarData = data
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSnackbarDismissState(snackbarHostState: SnackbarHostState): DismissState {
    return rememberDismissState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.DismissedToEnd -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    true
                }
                DismissValue.DismissedToStart -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    true
                }
                else -> { false }
            }
        }
    )
}