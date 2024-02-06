package com.hellguy39.hellnotes.core.ui.components.snack

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSnackbarHost(state: SnackbarHostState) {
    SnackbarHost(hostState = state) { data ->
        DismissableSnackbar(
            dismissState = rememberSnackbarDismissState(snackbarHostState = state),
            snackbarData = data,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSnackbarDismissState(snackbarHostState: SnackbarHostState): SwipeToDismissBoxState {
    return rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    true
                }
                else -> {
                    false
                }
            }
        },
    )
}
