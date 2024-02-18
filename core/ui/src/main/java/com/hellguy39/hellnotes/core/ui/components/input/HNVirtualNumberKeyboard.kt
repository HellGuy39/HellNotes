package com.hellguy39.hellnotes.core.ui.components.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.AppIcons

@Composable
fun HNVirtualNumberKeyboard(
    selection: NumberKeyboardSelection,
    disabledButtonKeys: List<String> = listOf(),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally,
            ),
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_1,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_2,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_3,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally,
            ),
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_4,
            onClick = selection.onClick,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_5,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_6,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally,
            ),
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_7,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_8,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_9,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally,
            ),
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_BACKSPACE,
            onClick = selection.onClick,
            onLongClick = selection.onLongClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_0,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KEY_ENTER,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys,
        )
    }
}

data class NumberKeyboardSelection(
    val onClick: (key: String) -> Unit,
    val onLongClick: (key: String) -> Unit,
)

@Composable
fun KeyboardNumberButton(
    key: String = "",
    onClick: (key: String) -> Unit,
    onLongClick: (key: String) -> Unit = {},
    disabledButtonKeys: List<String> = listOf(),
) {
    when (key) {
        NumberKeyboardKeys.KEY_BACKSPACE -> {
            FilledIconButton(
                onClick = { onClick(key) },
                enabled = !disabledButtonKeys.contains(key),
                modifier = Modifier.size(width = 84.dp, height = 84.dp),
                shape = CircleShape,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.Backspace),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
        NumberKeyboardKeys.KEY_ENTER -> {
            FilledIconButton(
                enabled = !disabledButtonKeys.contains(key),
                onClick = { onClick(key) },
                modifier = Modifier.size(width = 84.dp, height = 84.dp),
                shape = CircleShape,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.KeyboardTab),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
        else -> {
            FilledTonalIconButton(
                enabled = !disabledButtonKeys.contains(key),
                onClick = { onClick(key) },
                modifier = Modifier.size(width = 84.dp, height = 84.dp),
                shape = CircleShape,
            ) {
                Text(
                    text = key,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}

object NumberKeyboardKeys {
    const val KEY_1 = "1"
    const val KEY_2 = "2"
    const val KEY_3 = "3"
    const val KEY_4 = "4"
    const val KEY_5 = "5"
    const val KEY_6 = "6"
    const val KEY_7 = "7"
    const val KEY_8 = "8"
    const val KEY_9 = "9"
    const val KEY_0 = "0"
    const val KEY_BACKSPACE = "backspace"
    const val KEY_ENTER = "enter"
}
