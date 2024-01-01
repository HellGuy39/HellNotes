package com.hellguy39.hellnotes.core.ui.components.snack

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
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

@Composable
fun SnackAction.getSnackMessage(isSingleItem: Boolean = true) =
    when (this) {
        SnackAction.Archive -> {
            stringResource(id = AppStrings.Snack.NoteArchived)
        }
        SnackAction.Delete -> {
            stringResource(id = AppStrings.Snack.NoteMovedToTrash)
        }
        SnackAction.Unarchive -> {
            stringResource(id = AppStrings.Snack.NoteUnarchived)
        }
        SnackAction.Restore -> {
            ""
        }
        SnackAction.Pinned -> {
            stringResource(AppStrings.Snack.NotePinned)
        }
        SnackAction.Unpinned -> {
            stringResource(AppStrings.Snack.NotePinned)
        }
    }

fun SnackAction.getSnackMessage(
    context: Context,
    isSingleItem: Boolean = true,
) = when (this) {
    SnackAction.Archive -> {
        context.getString(AppStrings.Snack.NoteArchived)
    }
    SnackAction.Delete -> {
        context.getString(AppStrings.Snack.NoteMovedToTrash)
    }
    SnackAction.Unarchive -> {
        context.getString(AppStrings.Snack.NoteUnarchived)
    }
    SnackAction.Restore -> {
        ""
    }
    SnackAction.Pinned -> {
        context.getString(AppStrings.Snack.NotePinned)
    }
    SnackAction.Unpinned -> {
        context.getString(AppStrings.Snack.NoteUnpinned)
    }
}
