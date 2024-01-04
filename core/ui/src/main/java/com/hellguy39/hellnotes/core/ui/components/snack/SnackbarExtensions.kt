package com.hellguy39.hellnotes.core.ui.components.snack

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun SnackbarHostState.showDismissableSnackbar(
    scope: CoroutineScope,
    message: String = "",
    actionLabel: String? = null,
    onActionPerformed: () -> Unit = {},
    duration: SnackbarDuration = SnackbarDuration.Long,
) {
    currentSnackbarData?.dismiss()
    scope.launch {
        showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration,
            withDismissAction = false,
        ).let { result ->
            when (result) {
                SnackbarResult.ActionPerformed -> onActionPerformed()
                SnackbarResult.Dismissed -> currentSnackbarData?.dismiss()
                else -> Unit
            }
        }
    }
}
