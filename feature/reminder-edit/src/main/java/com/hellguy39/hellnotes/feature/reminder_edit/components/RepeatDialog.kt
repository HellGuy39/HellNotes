package com.hellguy39.hellnotes.feature.reminder_edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.Repeat
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.CustomDialogState
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItem
import com.hellguy39.hellnotes.core.ui.getDisplayName
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@Composable
fun RepeatDialog(
    state: CustomDialogState,
    repeatDialogSelection: RepeatDialogSelection
) {
    CustomDialog(
        showDialog = state.visible,
        onClose = { state.dismiss() },
    ) {
        val repeats = listOf(Repeat.DoesNotRepeat, Repeat.Daily, Repeat.Weekly, Repeat.Monthly)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(repeats) { repeat ->
                val isSelected = repeat == repeatDialogSelection.repeat
                SelectionItem(
                    title = repeat.getDisplayName(),
                    heroIcon = if (isSelected) painterResource(id = HellNotesIcons.Done) else null,
                    onClick = { repeatDialogSelection.onRepeatChange(repeat) },
                    colorize = isSelected
                )
            }
        }
    }
}

data class RepeatDialogSelection(
    val repeat: Repeat,
    val onRepeatChange: (Repeat) -> Unit
)