package com.hellguy39.hellnotes.core.ui.component.dialog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hellguy39.hellnotes.core.ui.value.elevation
import com.hellguy39.hellnotes.core.ui.window.WindowInfo
import com.hellguy39.hellnotes.core.ui.window.rememberWindowInfo

@Composable
fun HNAdaptiveDialog(
    isOpen: Boolean = false,
    onClose: () -> Unit = { },
    windowInfo: WindowInfo = rememberWindowInfo(),
    content: @Composable () -> Unit = {  }
) {
    when(windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> {
            FullScreenDialog(
                isOpen = isOpen,
                onClose = onClose,
                content = content
            )
        }
        else -> {
            BasicDialog(
                isOpen = isOpen,
                onClose = onClose,
                content = content
            )
        }
    }
}

@Composable
private fun FullScreenDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    if (!isOpen)
        return

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onClose,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(MaterialTheme.elevation.level3)
        ) {
            content()
        }
    }
}

@Composable
private fun BasicDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    if (!isOpen)
        return

    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onClose,
    ) {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.surface
        ) {
            content()
        }
    }
}
