package com.hellguy39.hellnotes.core.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenDialog(
    isShowingDialog: Boolean,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties =
                DialogProperties(
                    dismissOnBackPress = dismissOnBackPress,
                    dismissOnClickOutside = dismissOnClickOutside,
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false,
                ),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
            ) {
                content()
            }
        }
    }
}
