package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun NoteChecklistGroup(
    modifier: Modifier = Modifier,
    checklists: List<Checklist> = listOf(),
) {
    checklists.first().let { checklist ->
        val items = checklist.items.filter { item -> !item.isChecked }
        val uncheckedCount = if (items.size > 3) items.size - 3 else 0

        Column {
            Card(
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (checklist.name.isNotEmpty() && checklist.name.isNotBlank()) {
                        Text(
                            text = checklist.name,
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    repeat(if (items.size > 3) 3 else items.size) { index ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = AppIcons.CheckboxUnchecked),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = items[index].text,
                                modifier = Modifier,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                    if (uncheckedCount >= 1) {
                        Text(
                            text =
                                if (uncheckedCount == 1) {
                                    stringResource(id = AppStrings.Subtitle.UncheckedItem, uncheckedCount)
                                } else {
                                    stringResource(id = AppStrings.Subtitle.UncheckedItems, uncheckedCount)
                                },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
            if (checklists.size > 1) {
                Spacer(modifier = Modifier.height(8.dp))
                val count = checklists.size - 1
                Text(
                    text =
                        if (count == 1) {
                            stringResource(id = AppStrings.Subtitle.Checklist, count)
                        } else {
                            stringResource(id = AppStrings.Subtitle.Checklists, count)
                        },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}
