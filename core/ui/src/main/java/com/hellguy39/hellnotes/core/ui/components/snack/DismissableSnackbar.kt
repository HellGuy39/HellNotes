package com.hellguy39.hellnotes.core.ui.components.snack

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissableSnackbar(
    dismissState: SwipeToDismissBoxState,
    snackbarData: SnackbarData,
) {
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        backgroundContent = { /* no-op */ },
    ) {
        CustomSnackbar(data = snackbarData)
    }
}
