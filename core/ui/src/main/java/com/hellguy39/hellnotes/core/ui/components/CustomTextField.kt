package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import com.hellguy39.hellnotes.core.ui.UiDefaults

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    isSingleLine: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        maxLines = 1,
        singleLine = isSingleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            if (value.isEmpty() && (hint.isNotEmpty() && hint.isNotBlank())) {
                Text(
                    text = hint,
                    style = textStyle.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.alpha(UiDefaults.Alpha.Hint)
                )
            }
            innerTextField()
        },
        keyboardActions = keyboardActions
    )
}