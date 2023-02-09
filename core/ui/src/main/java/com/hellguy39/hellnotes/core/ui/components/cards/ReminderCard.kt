package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReminderCard(
    remind: Remind,
    isSelected: Boolean = false,
    selection: ReminderCardSelection,
    dateHelper: DateHelper
) {
    val cardBorder = if (isSelected)
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else
            CardDefaults.outlinedCardBorder()

    OutlinedCard(
        //onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = { selection.onClick(remind) },
                onLongClick = { selection.onLongClick(remind) }
            ),
        border = cardBorder
    ) {
        Column(
            modifier = Modifier
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = dateHelper.epochMillisToFormattedDate(remind.triggerDate),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = remind.message,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FilledTonalIconButton(
                    onClick = { selection.onEditButtonClick(remind) }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Edit),
                        contentDescription = null
                    )
                }
                FilledTonalIconButton(
                    onClick = { selection.onDeleteButtonClick(remind) }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Cancel),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

data class ReminderCardSelection(
    val onClick: (remind: Remind) -> Unit,
    val onLongClick: (remind: Remind) -> Unit,
    val onDeleteButtonClick: (remind: Remind) -> Unit,
    val onEditButtonClick: (remind: Remind) -> Unit
)