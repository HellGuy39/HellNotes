package com.hellguy39.hellnotes.core.ui.component.snack

sealed class SnackAction {
    object None: SnackAction()
    object Delete: SnackAction()
    object Restore: SnackAction()
    object Archive: SnackAction()
    object Unarchive: SnackAction()
    object Pinned: SnackAction()
    object Unpinned: SnackAction()
}
