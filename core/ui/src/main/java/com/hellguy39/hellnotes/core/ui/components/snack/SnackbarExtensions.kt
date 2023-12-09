package com.hellguy39.hellnotes.core.ui.components.snack

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
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
                stringResource(id = HellNotesStrings.Snack.NoteArchived)
            } else {
                stringResource(id = HellNotesStrings.Snack.NotesArchived)
            }
        }
        SnackAction.Delete -> {
            if (isSingleItem) {
                stringResource(id = HellNotesStrings.Snack.NoteMovedToTrash)
            } else {
                stringResource(id = HellNotesStrings.Snack.NotesMovedToTrash)
            }
        }
        SnackAction.Unarchive -> {
            if (isSingleItem) {
                stringResource(id = HellNotesStrings.Snack.NoteUnarchived)
            } else {
                stringResource(id = HellNotesStrings.Snack.NotesUnarchived)
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
            stringResource(HellNotesStrings.Snack.NotePinned)
        }
        SnackAction.Unpinned -> {
            stringResource(HellNotesStrings.Snack.NotePinned)
        }
    }

fun SnackAction.getSnackMessage(
    context: Context,
    isSingleItem: Boolean = true,
) = when (this) {
    SnackAction.Archive -> {
        if (isSingleItem) {
            context.getString(HellNotesStrings.Snack.NoteArchived)
        } else {
            context.getString(HellNotesStrings.Snack.NotesArchived)
        }
    }
    SnackAction.Delete -> {
        if (isSingleItem) {
            context.getString(HellNotesStrings.Snack.NoteMovedToTrash)
        } else {
            context.getString(HellNotesStrings.Snack.NotesMovedToTrash)
        }
    }
    SnackAction.Unarchive -> {
        if (isSingleItem) {
            context.getString(HellNotesStrings.Snack.NoteUnarchived)
        } else {
            context.getString(HellNotesStrings.Snack.NotesUnarchived)
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
        context.getString(HellNotesStrings.Snack.NotePinned)
    }
    SnackAction.Unpinned -> {
        context.getString(HellNotesStrings.Snack.NoteUnpinned)
    }
}
