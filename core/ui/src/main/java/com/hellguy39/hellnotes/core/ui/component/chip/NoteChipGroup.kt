package com.hellguy39.hellnotes.core.ui.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import HNDateHandler
import com.hellguy39.hellnotes.core.model.local.database.Label
import com.hellguy39.hellnotes.core.model.local.database.Reminder
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NoteChipGroup(
    modifier: Modifier = Modifier,
    reminders: List<Reminder>,
    labels: List<Label>,
    limitElements: Boolean = false,
    maxElements: Int = 2,
    onRemindClick: (reminder: Reminder) -> Unit = {},
    onLabelClick: (label: Label) -> Unit = {},
    mainAxisSpacing: Dp = 8.dp,
    crossAxisSpacing: Dp = 8.dp
) {
    val chipsCount = reminders.size + labels.size
    var counter = 0

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(mainAxisSpacing),
//        mainAxisSpacing = mainAxisSpacing,
//        crossAxisSpacing = crossAxisSpacing
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
                modifier = Modifier.height(FilterChipDefaults.Height),
                selected = true,
                onClick = {
                    onRemindClick(reminder)
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(FilterChipDefaults.IconSize),
                        painter = painterResource(id = HellNotesIcons.Alarm),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = HNDateHandler.from(reminder.triggerDate).formatBest(),
                        style = MaterialTheme.typography.labelLarge
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
                modifier = Modifier.height(FilterChipDefaults.Height),
                selected = true,
                onClick = {
                    onLabelClick(label)
                },
                label = {
                    Text(
                        text = label.name,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
            )
        }

        if ((limitElements && counter >= maxElements) && (chipsCount - counter) != 0) {
            FilterChip(
                modifier = Modifier.height(FilterChipDefaults.Height),
                selected = true,
                onClick = {},
                label = {
                    Text(
                        text = "+${chipsCount - counter}",
                        style = MaterialTheme.typography.labelLarge
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun NoteChipGroupPreview() {
    HellNotesTheme {
        NoteChipGroup(
            reminders = listOf(),
            labels = listOf(
                Label(name = "123"),
                Label(name = "123")
            )
        )
    }
}