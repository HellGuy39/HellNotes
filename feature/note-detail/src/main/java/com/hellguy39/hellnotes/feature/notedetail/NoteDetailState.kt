package com.hellguy39.hellnotes.feature.notedetail

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.state.resources
import com.hellguy39.hellnotes.feature.notedetail.util.ShareType
import com.hellguy39.hellnotes.feature.notedetail.util.ShareUtils
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberNoteDetailState(
    context: Context = LocalContext.current,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = remember(context, resources, coroutineScope, snackbarHostState) {
    HomeState(
        context = context,
        resources = resources,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState,
    )
}

@Stable
class HomeState(
    val context: Context,
    val resources: Resources,
    val coroutineScope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
) {
    fun showSnack(
        text: UiText,
        onActionPerformed: () -> Unit = {},
        withUndo: Boolean = false,
    ) {
        snackbarHostState.showDismissableSnackbar(
            scope = coroutineScope,
            message = text.asString(resources),
            actionLabel = if (withUndo) resources.getString(AppStrings.Button.Undo) else null,
            duration = SnackbarDuration.Long,
            onActionPerformed = onActionPerformed,
        )
    }

    fun shareNote(
        shareType: ShareType,
        noteWrapper: NoteWrapper,
    ) {
        ShareUtils.share(
            context = context,
            note = noteWrapper.note,
            type = shareType,
        )
    }

    fun showToast(uiText: UiText) {
        Toast.makeText(
            context,
            uiText.asString(context),
            Toast.LENGTH_SHORT,
        ).show()
    }
}
