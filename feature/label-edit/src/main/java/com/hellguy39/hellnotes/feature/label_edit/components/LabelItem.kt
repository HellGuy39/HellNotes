package com.hellguy39.hellnotes.feature.label_edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.HNIconButton
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@Composable
fun LabelItem(
    label: Label,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onLabelChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var isFocused by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(label.name) }

    fun saveLabel() {
        if (text.isNotBlank() && text.isNotEmpty()) {
            onLabelChange(text)
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HNIconButton(
            enabled = isFocused,
            onClick = onDelete,
            enabledPainter = painterResource(id = HellNotesIcons.Delete),
            disabledPainter = painterResource(id = HellNotesIcons.Label)
        )

        HNClearTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { state -> isFocused = state.isFocused },
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardActions = KeyboardActions(
                onDone = {
                    saveLabel()
                    focusManager.clearFocus()
                }
            )
        )

        HNIconButton(
            enabled = isFocused,
            onClick = {
                saveLabel()
                focusManager.clearFocus()
            },
            enabledPainter = painterResource(id = HellNotesIcons.Done)
        )
    }

    CustomDivider(isVisible = isFocused)
}