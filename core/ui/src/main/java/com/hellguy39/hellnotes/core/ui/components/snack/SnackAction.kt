package com.hellguy39.hellnotes.core.ui.components.snack

sealed class SnackAction {
    object Delete: SnackAction()
    object Archive: SnackAction()
    object Unarchive: SnackAction()
}
