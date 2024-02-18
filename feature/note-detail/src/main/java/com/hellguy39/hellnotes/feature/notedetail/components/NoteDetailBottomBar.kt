package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Elevation
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailBottomBarSelection
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailUiState

@Composable
fun NoteDetailBottomBar(
    uiState: NoteDetailUiState,
    lazyListState: LazyListState,
    bottomBarSelection: NoteDetailBottomBarSelection,
) {
    val isAtBottom = !lazyListState.canScrollForward
    val fraction = if (isAtBottom) 1f else 0f
    val appBarContainerColor by animateColorAsState(
        targetValue =
            lerp(
                MaterialTheme.colorScheme.surfaceColorAtElevation(Elevation.Level2),
                MaterialTheme.colorScheme.surfaceColorAtElevation(Elevation.Level0),
                FastOutLinearInEasing.transform(fraction),
            ),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "appBarContainerColor",
    )

    Surface(
        modifier =
            Modifier
                .fillMaxWidth(),
        color = appBarContainerColor,
    ) {
        Row(
            modifier =
                Modifier
                    .imePadding()
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = bottomBarSelection.onAttachment,
                enabled = !uiState.isReadOnly,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.Attachment),
                    contentDescription = null,
                )
            }

            Text(
                text =
                    stringResource(
                        id = AppStrings.Subtitle.Edited,
                        formatArgs =
                            arrayOf(
                                DateTimeUtils.formatBest(uiState.wrapper.note.editedAt),
                            ),
                    ),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
            )

            IconButton(
                onClick = bottomBarSelection.onMenu,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.MoreVert),
                    contentDescription = null,
                )
            }
        }
    }
}
