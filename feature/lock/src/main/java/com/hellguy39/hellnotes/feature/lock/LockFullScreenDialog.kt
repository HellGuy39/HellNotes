package com.hellguy39.hellnotes.feature.lock

import androidx.compose.runtime.Composable
import com.hellguy39.hellnotes.core.ui.components.dialog.FullScreenDialog

@Composable
fun LockFullScreenDialog(
    isShowDialog: Boolean,
) {
    FullScreenDialog(
        isShowingDialog = isShowDialog,
        onDismissRequest = { /* Ignore */ },
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
    ) {
        LockRoute()
    }
}
