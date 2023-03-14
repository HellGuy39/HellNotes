package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import java.io.Serializable

@Composable
fun CustomDialog(
    state: CustomDialogState = rememberDialogState(),
    onClose: () -> Unit = { state.dismiss() },
    heroIcon: Painter? = null,
    title: String = "",
    message: String = "",
    onCancel: (() -> Unit)? = null,
    onAccept: (() -> Unit)? = null,
    limitMaxHeight: Boolean = true,
    limitMinHeight: Boolean = false,
    content: (@Composable () -> Unit)? = null
) {
    if (!state.visible)
        return

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level3)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(
                        minHeight = if (limitMinHeight) 384.dp else Dp.Unspecified,
                        maxHeight = if (limitMaxHeight) 384.dp else Dp.Unspecified
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp, alignment = Alignment.CenterVertically
                    )
                ) {
                    if (heroIcon != null) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = heroIcon,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = null
                        )
                    }
                    if (title.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                    if (message.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                    }
                }
                if (content != null) {
                    Column {
                        CustomDivider(paddingValues = PaddingValues(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline)
                        content()
                        CustomDivider(paddingValues = PaddingValues(horizontal = 16.dp), color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                if (onCancel != null || onAccept != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp, alignment = Alignment.End
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (onCancel != null) {
                            TextButton(
                                onClick = onCancel,
                            ) {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Button.Cancel),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                        if (onAccept != null) {
                            Button(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = onAccept,
                            ) {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Button.Accept),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberDialogState(): CustomDialogState {
    return rememberSaveable(
        saver = CustomDialogState.saver(),
        init = {
            CustomDialogState(
                visible = false
            )
        }
    )
}


class CustomDialogState(
    visible: Boolean,
) {
    var visible by mutableStateOf(visible)

    fun show() {
        visible = true
    }

    fun dismiss() {
        visible = false
    }

    data class CustomDialogStateData(
        val visible: Boolean
    ): Serializable

    companion object {

        fun saver(): Saver<CustomDialogState, *> = Saver(
            save = { state ->
                CustomDialogStateData(
                    visible = state.visible
                )
            },
            restore = { data ->
                CustomDialogState(
                    visible = data.visible
                )
            }
        )
    }
}