package com.hellguy39.hellnotes.feature.lock

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.hellguy39.hellnotes.core.ui.components.dialog.FullScreenDialog

@Composable
fun LockFullScreenDialog(
    isShowDialog: Boolean,
) {
    val activity = LocalContext.current as FragmentActivity
    FullScreenDialog(
        isShowingDialog = isShowDialog,
        onDismissRequest = { /* Ignore */ },
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
    ) {
        LockRoute(activity)
    }
}
