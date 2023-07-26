package com.hellguy39.hellnotes.core.ui.component.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedPicker(
    modifier: Modifier = Modifier,
    values: List<String>,
    selected: String,
) {
//    Box(
//        modifier = modifier,
//        contentAlignment = Alignment.Center
//    ) {
//        Surface(
//            modifier = Modifier
//                .padding(vertical = 16.dp, horizontal = 16.dp)
//                .clip(shape = RoundedCornerShape(24.dp)),
//            color = MaterialTheme.colorScheme.onSurface
//        ) {
//            Row(
//                modifier = Modifier
//                    .padding(horizontal = 4.dp, vertical = 4.dp),
//                horizontalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .width(96.dp)
//                        .clip(RoundedCornerShape(24.dp))
//                        .clickable(
//                            enabled = true,
//                            onClick = { selection.onThemeToggle(ThemeState.Light) }
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .padding(8.dp),
//                        text = "Light",
//                        color = MaterialTheme.colorScheme.surface,
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }
//                Box(
//                    modifier = Modifier
//                        .width(96.dp)
//                        .clip(shape = RoundedCornerShape(24.dp))
//                        .clickable(
//                            enabled = true,
//                            onClick = { selection.onThemeToggle(ThemeState.Dark) }
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .padding(8.dp),
//                        text = "Dark",
//                        color = MaterialTheme.colorScheme.surface,
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }
//                Box(
//                    modifier = Modifier
//                        .width(96.dp)
//                        .clip(shape = RoundedCornerShape(24.dp))
//                        .background(color = MaterialTheme.colorScheme.surface)
//                        .clickable(
//                            enabled = true,
//                            onClick = { selection.onThemeToggle(ThemeState.System) }
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        modifier = Modifier.padding(8.dp),
//                        text = "System",
//                        color = MaterialTheme.colorScheme.onSurface,
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }
//            }
//        }
//    }
}