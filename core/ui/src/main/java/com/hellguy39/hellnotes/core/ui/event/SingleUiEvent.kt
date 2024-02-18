package com.hellguy39.hellnotes.core.ui.event

import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText

sealed interface SingleUiEvent {
    data class ShowSnackbar(val text: UiText) : SingleUiEvent

    data class ShowSnackbarWithAction(val text: UiText) : SingleUiEvent
}
