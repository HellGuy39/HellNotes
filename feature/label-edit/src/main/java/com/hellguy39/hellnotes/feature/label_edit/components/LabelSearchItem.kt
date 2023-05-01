package com.hellguy39.hellnotes.feature.label_edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun LabelSearchItem(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onCreateLabel: (Label) -> Boolean
) {
    val focusManager = LocalFocusManager.current

    var isFocused by remember { mutableStateOf(false) }
    var newLabel by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Crossfade(isFocused) { isFocused ->
            if (isFocused) {
                IconButton(
                    onClick = {
                        newLabel = ""
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Close),
                        contentDescription = null
                    )
                }
            } else {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Add),
                        contentDescription = null
                    )
                }
            }
        }
        HNClearTextField(
            value = newLabel,
            onValueChange = { newText -> newLabel = newText },
            hint = stringResource(id = HellNotesStrings.Hint.CreateNewLabel),
            modifier = Modifier
                .focusRequester(focusRequester)
                .weight(1f)
                .onFocusChanged { state -> isFocused = state.isFocused },
            textStyle = MaterialTheme.typography.bodyLarge
        )
        AnimatedVisibility (isFocused) {
            IconButton(
                onClick = {
                    if (newLabel.isNotBlank() && newLabel.isNotEmpty()) {
                        val label = Label(name = newLabel)
                        val result = onCreateLabel(label)
                        if (result) {
                            newLabel = ""
                            focusManager.clearFocus()
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Done),
                    contentDescription = null
                )
            }
        }
    }

    CustomDivider(isVisible = isFocused)
}