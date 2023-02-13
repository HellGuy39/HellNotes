package com.hellguy39.hellnotes.core.ui.components.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@Composable
fun NumberKeyboard(
    selection: NumberKeyboardSelection,
    disabledButtonKeys: List<String> = listOf()
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key1,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key2,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key3,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key4,
            onClick = selection.onClick)
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key5,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key6,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key7,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key8,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key9,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KeyBio,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.Key0,
            onClick = selection.onClick,
            disabledButtonKeys = disabledButtonKeys
        )
        KeyboardNumberButton(
            key = NumberKeyboardKeys.KeyBackspace,
            onClick = selection.onClick,
            onLongClick = selection.onLongClick,
            disabledButtonKeys = disabledButtonKeys
        )
    }
    Spacer(modifier = Modifier.size(width = 0.dp, height = 16.dp))
//        Button(
//            onClick = { },
//            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
//        ) {
//            Icon(
//                painter = painterResource(id = HellNotesIcons.Fingerprint),
//                contentDescription = null,
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//            )
//            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//            Text(text = "Use biometric")
//        }
}

data class NumberKeyboardSelection(
    val onClick: (key: String) -> Unit,
    val onLongClick: (key: String) -> Unit
)

@Composable
fun KeyboardNumberButton(
    key: String = "",
    onClick: (key: String) -> Unit,
    onLongClick: (key: String) -> Unit = {},
    disabledButtonKeys: List<String> = listOf()
) {
    when (key) {
        NumberKeyboardKeys.KeyBackspace -> {
            FilledIconButton(
                onClick = { onClick(key) },
                enabled = !disabledButtonKeys.contains(key),
                modifier = Modifier.size(width = 84.dp, height = 84.dp),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Backspace),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        NumberKeyboardKeys.KeyBio -> {
            FilledIconButton(
                enabled = !disabledButtonKeys.contains(key),
                onClick = { onClick(key) },
                modifier = Modifier.size(width = 84.dp, height = 84.dp),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Fingerprint),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        else -> {
            FilledTonalIconButton(
                enabled = !disabledButtonKeys.contains(key),
                onClick = { onClick(key) },
                modifier = Modifier.size(width = 84.dp, height = 84.dp),
                shape = CircleShape
            ) {
                Text(
                    text = key,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

object NumberKeyboardKeys {
    const val Key1 = "1"
    const val Key2 = "2"
    const val Key3 = "3"
    const val Key4 = "4"
    const val Key5 = "5"
    const val Key6 = "6"
    const val Key7 = "7"
    const val Key8 = "8"
    const val Key9 = "9"
    const val Key0 = "0"
    const val KeyBackspace = "backspace"
    const val KeyBio = "bio"
}