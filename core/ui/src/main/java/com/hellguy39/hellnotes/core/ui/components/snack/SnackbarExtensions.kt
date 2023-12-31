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
            if (isSingleItem) {
                stringResource(id = AppStrings.Snack.NoteArchived)
            } else {
                stringResource(id = AppStrings.Snack.NotesArchived)
            }
        }
        SnackAction.Delete -> {
            if (isSingleItem) {
                stringResource(id = AppStrings.Snack.NoteMovedToTrash)
            } else {
                stringResource(id = AppStrings.Snack.NotesMovedToTrash)
            }
        }
        SnackAction.Unarchive -> {
            if (isSingleItem) {
                stringResource(id = AppStrings.Snack.NoteUnarchived)
            } else {
                stringResource(id = AppStrings.Snack.NotesUnarchived)
            }
        }
        SnackAction.Restore -> {
            if (isSingleItem) {
                ""
            } else {
                ""
            }
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
        if (isSingleItem) {
            context.getString(AppStrings.Snack.NoteArchived)
        } else {
            context.getString(AppStrings.Snack.NotesArchived)
        }
    }
    SnackAction.Delete -> {
        if (isSingleItem) {
            context.getString(AppStrings.Snack.NoteMovedToTrash)
        } else {
            context.getString(AppStrings.Snack.NotesMovedToTrash)
        }
    }
    SnackAction.Unarchive -> {
        if (isSingleItem) {
            context.getString(AppStrings.Snack.NoteUnarchived)
        } else {
            context.getString(AppStrings.Snack.NotesUnarchived)
        }
    }
    SnackAction.Restore -> {
        if (isSingleItem) {
            ""
        } else {
            ""
        }
    }
    SnackAction.Pinned -> {
        context.getString(AppStrings.Snack.NotePinned)
    }
    SnackAction.Unpinned -> {
        context.getString(AppStrings.Snack.NoteUnpinned)
    }
}
