package com.hellguy39.hellnotes.core.ui.components.snack

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun showSnackbar(
    scope: CoroutineScope,
    hostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    onActionPerformed: () -> Unit
) {
    scope.launch {
        hostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long,
            withDismissAction = false
        ).let { result ->
            when (result) {
                SnackbarResult.ActionPerformed -> onActionPerformed()
                SnackbarResult.Dismissed -> hostState.currentSnackbarData?.dismiss()
                else -> Unit
            }
        }
    }
}

@Composable
fun getSnackMessage(snackAction: SnackAction, isSingleItem: Boolean) = when(snackAction) {
    SnackAction.Archive -> {
        if (isSingleItem)
            stringResource(id = HellNotesStrings.Snack.NoteArchived)
        else
            stringResource(id = HellNotesStrings.Snack.NotesArchived)
    }
    SnackAction.Delete -> {
        if (isSingleItem)
            stringResource(id = HellNotesStrings.Snack.NoteMovedToTrash)
        else
            stringResource(id = HellNotesStrings.Snack.NotesMovedToTrash)
    }
    SnackAction.Unarchive -> {
        if (isSingleItem)
            stringResource(id = HellNotesStrings.Snack.NoteUnarchived)
        else
            stringResource(id = HellNotesStrings.Snack.NotesUnarchived)
    }
}

fun getSnackMessage(snackAction: SnackAction, context: Context, isSingleItem: Boolean) = when(snackAction) {
    SnackAction.Archive -> {
        if (isSingleItem)
            context.getString(HellNotesStrings.Snack.NoteArchived)
        else
            context.getString(HellNotesStrings.Snack.NotesArchived)
    }
    SnackAction.Delete -> {
        if (isSingleItem)
            context.getString(HellNotesStrings.Snack.NoteMovedToTrash)
        else
            context.getString(HellNotesStrings.Snack.NotesMovedToTrash)
    }
    SnackAction.Unarchive -> {
        if (isSingleItem)
            context.getString(HellNotesStrings.Snack.NoteUnarchived)
        else
            context.getString(HellNotesStrings.Snack.NotesUnarchived)
    }
}