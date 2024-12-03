/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
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
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailUiEvent
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailUiState

@Composable
fun NoteDetailBottomBar(
    uiState: NoteDetailUiState,
    lazyListState: LazyListState,
    onUiEvent: (event: NoteDetailUiEvent) -> Unit,
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
            Row {
                IconButton(
                    onClick = { onUiEvent(NoteDetailUiEvent.ShowAttachment(true)) },
                    enabled = !uiState.isReadOnly,
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Attachment),
                        contentDescription = null,
                    )
                }
//
//                IconButton(
//                    onClick = {},
//                    enabled = true,
//                ) {
//                    Icon(
//                        painter = painterResource(id = AppIcons.Undo),
//                        contentDescription = null,
//                    )
//                }
//
//                IconButton(
//                    onClick = {},
//                    enabled = true,
//                ) {
//                    Icon(
//                        painter = painterResource(id = AppIcons.Redo),
//                        contentDescription = null,
//                    )
//                }
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
                onClick = { onUiEvent(NoteDetailUiEvent.ShowMenu(true)) },
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.MoreVert),
                    contentDescription = null,
                )
            }
        }
    }
}
