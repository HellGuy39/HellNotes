package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.CustomSwitch
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteSwipeEditScreen(
    onNavigationButtonClick: () -> Unit,
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        topBar = {
            CustomLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = "Note swipes to quick actions"
            )
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                item {
                    var isChecked by remember { mutableStateOf(false) }

                    val fraction = if (isChecked) 1f else 0f
                    val containerColor by animateColorAsState(
                        targetValue = lerp(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.primaryContainer,
                            FastOutSlowInEasing.transform(fraction)
                        ),
                        animationSpec = tween(200)//spring(stiffness = Spring.StiffnessLow)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                        ) {
                            CustomSwitch(
                                modifier = Modifier.fillMaxWidth(),
                                title = "Use Note swipes",
                                checked = isChecked,
                                onCheckedChange = { checked -> isChecked = checked}
                            )
                        }
                    }
                }
                item {
                    SectionHeader(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = "Swipe left",
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = false, onClick = {  })
                        Text(
                            text = "Delete/Restore",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = false, onClick = {  })
                        Text(
                            text = "Archive/Unarchive",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }}
                item {
                    SectionHeader(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = "Swipe right",
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = false, onClick = {  })
                        Text(
                            text = "Delete/Restore",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = false, onClick = {  })
                        Text(
                            text = "Archive/Unarchive",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String = "",
    icon: Painter? = null,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = icon,
                contentDescription = null,
                tint = color
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                color = color
            )
        )
    }
}
