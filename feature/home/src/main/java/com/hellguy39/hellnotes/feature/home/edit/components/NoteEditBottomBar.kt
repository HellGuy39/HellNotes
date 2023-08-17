package com.hellguy39.hellnotes.feature.home.edit.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import HNDateHandler
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.edit.NoteWrapperState

@Composable
fun NoteEditBottomBar(
    noteWrapperState: NoteWrapperState,
    contentType: HNContentType,
    containerColor: Color,
    scrolledContainerColor: Color,
    lazyListState: LazyListState,
    bottomBarSelection: NoteEditBottomBarSelection
) {
    if (noteWrapperState !is NoteWrapperState.Success)
        return

    val noteWrapper = noteWrapperState.noteWrapper

    val isAtBottom = !lazyListState.canScrollForward
    val fraction = if (isAtBottom) 1f else 0f
    val appBarContainerColor by animateColorAsState(
        targetValue = lerp(
            scrolledContainerColor,
            containerColor,
            FastOutLinearInEasing.transform(fraction)
        ),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "label"
    )

    val modifier = if (contentType == HNContentType.DualPane) Modifier.navigationBarsPadding() else Modifier

    Surface(
        modifier = Modifier.fillMaxWidth()
            .then(modifier),
        color = appBarContainerColor
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = bottomBarSelection.onAttachment
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Attachment),
                    contentDescription = null
                )
            }

            Text(
                text = stringResource(
                    id = HellNotesStrings.Subtitle.Edited,
                    formatArgs = arrayOf(
                        HNDateHandler.from(noteWrapper.note.editedAt).formatBest()
                    )
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )


            IconButton(
                onClick = bottomBarSelection.onMenu
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.MoreVert),
                    contentDescription = null
                )
            }
        }
    }
}

data class NoteEditBottomBarSelection(
    val onMenu: () -> Unit,
    val onAttachment: () -> Unit,
)