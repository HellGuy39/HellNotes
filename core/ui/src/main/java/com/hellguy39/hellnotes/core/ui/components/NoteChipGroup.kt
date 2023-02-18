package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Reminder
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteChipGroup(
    modifier: Modifier = Modifier,
    reminders: List<Reminder>,
    labels: List<Label>,
    limitElements: Boolean = false,
    maxElements: Int = 2,
    onRemindClick: (reminder: Reminder) -> Unit = {},
    onLabelClick: (label: Label) -> Unit = {},
    dateHelper: DateHelper
) {
    val chipsCount = reminders.size + labels.size
    var counter = 0

    FlowRow(
        modifier = modifier,
        mainAxisSpacing = 12.dp,
        crossAxisSpacing = 0.dp,
    ) {
        for (i in reminders.indices) {
            val reminder = reminders[i]

            if (limitElements) {
                if (counter >= maxElements) {
                    break
                } else {
                    counter++
                }
            }

            FilterChip(
                selected = true,
                onClick = {
                    onRemindClick(reminder)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Alarm),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = dateHelper.epochMillisToFormattedDate(reminder.triggerDate),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
            )
        }

        for (i in labels.indices) {
            val label = labels[i]

            if (limitElements) {
                if (counter >= maxElements) {
                    break
                } else {
                    counter++
                }
            }

            FilterChip(
                selected = true,
                onClick = {
                    onLabelClick(label)
                },
                label = {
                    Text(
                        text = label.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
            )
        }

        if (limitElements && counter >= maxElements) {
            FilterChip(
                selected = true,
                onClick = {},
                label = {
                    Text(
                        text = "+${chipsCount - counter}",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
            )
        }

    }

}