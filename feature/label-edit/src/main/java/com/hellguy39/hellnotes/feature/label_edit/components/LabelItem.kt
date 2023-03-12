package com.hellguy39.hellnotes.feature.label_edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.LabelItem(
    label: Label,
    selection: LabelItemSelection
) {
    val focusManager = LocalFocusManager.current

    var isFocused by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(label.name) }

    CustomDivider(isVisible = isFocused)

    Row(
        modifier = Modifier
            .padding(8.dp)
            .animateItemPlacement(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Crossfade(targetState = isFocused) { isFocused ->
            if (isFocused) {
                IconButton(
                    onClick = { selection.onDeleteLabel(label) }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Delete),
                        contentDescription = null
                    )
                }
            } else {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(id = HellNotesIcons.Label),
                    contentDescription = null
                )
            }
        }
        CustomTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { state -> isFocused = state.isFocused },
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (text.isNotBlank() && text.isNotEmpty()) {
                        focusManager.clearFocus()
                        selection.onLabelUpdated(label.copy(name = text))
                    }
                }
            )
        )
        AnimatedVisibility(visible = isFocused) {
            IconButton(
                onClick = {
                    if (text.isNotBlank() && text.isNotEmpty()) {
                        focusManager.clearFocus()
                        selection.onLabelUpdated(label.copy(name = text))
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

data class LabelItemSelection(
    val onCreateLabel: (Label) -> Unit,
    val onLabelUpdated: (Label) -> Unit,
    val onDeleteLabel: (Label) -> Unit
)