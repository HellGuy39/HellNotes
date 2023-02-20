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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import java.io.Serializable

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onClose: () -> Unit,
    title: String = "",
    limitMaxHeight: Boolean = true,
    limitMinHeight: Boolean = false,
    titleContent: @Composable () -> Unit = {  },
    content: @Composable () -> Unit
) {
    if (!showDialog)
        return

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .sizeIn(
                        minHeight = if (limitMinHeight) 384.dp else Dp.Unspecified,
                        maxHeight = if (limitMaxHeight) 384.dp else Dp.Unspecified
                    )
            ) {
                content()
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