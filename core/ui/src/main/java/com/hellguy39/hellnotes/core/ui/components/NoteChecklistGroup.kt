package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.*
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
import com.hellguy39.hellnotes.core.model.CheckItem
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun NoteChecklistGroup(
    modifier: Modifier = Modifier,
    checklist: List<CheckItem> = listOf()
) {
    val uncheckedCount = if (checklist.size > 3) checklist.size - 3 else 0
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(if (checklist.size > 3) 3 else checklist.size) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = HellNotesIcons.CheckboxUnchecked),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = checklist[index].text,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (uncheckedCount >= 1) {
            Text(
                text = if (uncheckedCount == 1)
                    stringResource(id = HellNotesStrings.Helper.UncheckedItem, uncheckedCount)
                else
                    stringResource(id = HellNotesStrings.Helper.UncheckedItems, uncheckedCount),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
