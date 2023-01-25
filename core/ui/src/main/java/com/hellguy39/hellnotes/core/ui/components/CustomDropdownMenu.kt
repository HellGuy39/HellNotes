package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import java.io.Serializable

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        content()
    }
}

@Composable
fun CustomDropdownItem(
    leadingIconId: Painter? = null,
    text: String = "",
    onClick: () -> Unit = {}
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        },
        onClick = onClick,
        leadingIcon = {
            if (leadingIconId != null) {
                Icon(
                    painter = leadingIconId,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun rememberDropdownMenuState(): CustomDropdownMenuState {
    return rememberSaveable(
        saver = CustomDropdownMenuState.saver(),
        init = {
            CustomDropdownMenuState(
                visible = false
            )
        }
    )
}

class CustomDropdownMenuState(
    visible: Boolean,
) {
    var visible by mutableStateOf(visible)

    fun show() {
        visible = true
    }

    fun dismiss() {
        visible = false
    }

    data class CustomDropdownMenuStateData(
        val visible: Boolean
    ): Serializable

    companion object {

        fun saver(): Saver<CustomDropdownMenuState, *> = Saver(
            save = { state ->
                CustomDropdownMenuStateData(
                    visible = state.visible
                )
            },
            restore = { data ->
                CustomDropdownMenuState(
                    visible = data.visible
                )
            }
        )
    }
}