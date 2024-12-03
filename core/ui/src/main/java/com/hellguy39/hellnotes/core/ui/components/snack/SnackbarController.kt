package com.hellguy39.hellnotes.core.ui.components.snack

import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarController {

    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }

}

data class SnackbarEvent(
    val text: UiText,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val text: UiText,
    val action: () -> Unit
) {
    companion object {
        fun undoAction(onUndo: () -> Unit) = SnackbarAction(
            text = UiText.StringResources(AppStrings.Button.Undo),
            action = onUndo
        )
    }
}
