package com.hellguy39.hellnotes.core.ui.components.snack

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissableSnackbar(
    dismissState: DismissState,
    snackbarData: SnackbarData,
) {
    SwipeToDismiss(
        state = dismissState,
        background = {},
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissContent = {
            CustomSnackbar(data = snackbarData)
        },
    )
}
