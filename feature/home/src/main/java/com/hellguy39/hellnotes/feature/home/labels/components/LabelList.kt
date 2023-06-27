package com.hellguy39.hellnotes.feature.home.labels.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.local.database.Label

@Composable
fun LabelList(
    modifier: Modifier,
    paddingValues: PaddingValues,
    items: List<Label>,
    onClick: (Label) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues,
        content = {
            items(
                items = items
            ) { label: Label ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .clickable(
                            enabled = true,
                            onClick = { onClick(label) },
                        ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 16.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = label.noteIds.size.toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Divider()
                }
            }
        }
    )
}