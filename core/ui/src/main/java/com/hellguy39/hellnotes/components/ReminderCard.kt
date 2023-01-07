package com.hellguy39.hellnotes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.common.date.DateHelper
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.resources.HellNotesIcons
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReminderCard(
    remind: Remind,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onEditClick: (remind: Remind) -> Unit,
    onDeleteClick: (remind: Remind) -> Unit
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
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onLongClick()
                }
            ),
        border = cardBorder
    ) {
        Column(
            modifier = Modifier
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = DateHelper(LocalContext.current)
                    .epochMillisToFormattedDate(remind.triggerDate),
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
                    onClick = { onEditClick(remind) }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Edit),
                        contentDescription = null
                    )
                }
                FilledTonalIconButton(
                    onClick = { onDeleteClick(remind) }
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