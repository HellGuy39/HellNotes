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
package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.hasAnythingToShow
import com.hellguy39.hellnotes.core.model.repository.local.database.isChecklistsValid
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.NoteChecklistGroup
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Duration
import com.hellguy39.hellnotes.core.ui.values.Elevation

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    noteWrapper: NoteWrapper,
    isSelected: Boolean = false,
    noteStyle: NoteStyle,
) {
    val fraction = if (isSelected) 1f else 0f

    val borderColor by animateColorAsState(
        targetValue =
            when (noteStyle) {
                is NoteStyle.Outlined -> outlinedCardBorderColorLerp(fraction = fraction)
                is NoteStyle.Elevated -> elevatedCardBorderColorLerp(fraction = fraction)
            },
        animationSpec = tween(Duration.FAST),
        label = "border_color",
    )

    val borderSize by animateDpAsState(
        targetValue =
            when (noteStyle) {
                is NoteStyle.Outlined -> outlinedCardBorderSizeLerp(fraction = fraction)
                is NoteStyle.Elevated -> elevatedCardBorderSizeLerp(fraction = fraction)
            },
        animationSpec = tween(Duration.FAST),
        label = "border_size",
    )

    val cardBorder by remember {
        derivedStateOf {
            BorderStroke(borderSize, borderColor)
        }
    }

    when (noteStyle) {
        is NoteStyle.Outlined -> {
            OutlinedCard(
                modifier = modifier,
                border = cardBorder,
            ) {
                NoteCardContent(
                    noteWrapper = noteWrapper,
                    onClick = onClick,
                    onLongClick = onLongClick,
                )
            }
        }
        is NoteStyle.Elevated -> {
            ElevatedCard(
                modifier =
                    modifier.border(
                        width = borderSize,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp),
                    ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = Elevation.Level2),
            ) {
                NoteCardContent(
                    noteWrapper = noteWrapper,
                    onClick = onClick,
                    onLongClick = onLongClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCardContent(
    noteWrapper: NoteWrapper,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    val isTitleVisible = noteWrapper.note.title.isNotEmpty() || noteWrapper.note.title.isNotBlank()
    val isNoteVisible = noteWrapper.note.note.isNotEmpty() || noteWrapper.note.note.isNotBlank()
    val isChipsVisible = noteWrapper.labels.isNotEmpty() || noteWrapper.reminders.isNotEmpty()
    val isChecklistVisible = noteWrapper.checklists.isChecklistsValid()
    val hasAnyVisibleContent = noteWrapper.hasAnythingToShow()

    Box(
        modifier =
            Modifier
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongClick()
                    },
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (isTitleVisible) {
                Text(
                    text = noteWrapper.note.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (isNoteVisible || !hasAnyVisibleContent) {
                Text(
                    text = if (!hasAnyVisibleContent) stringResource(id = AppStrings.Body.NewNote) else noteWrapper.note.note,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    modifier = Modifier,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (isChecklistVisible) {
                NoteChecklistGroup(
                    checklists = noteWrapper.checklists,
                )
            }

            if (isChipsVisible) {
                NoteChipGroup(
                    modifier = Modifier,
                    reminders = noteWrapper.reminders,
                    labels = noteWrapper.labels,
                    limitElements = true,
                    maxElements = 2,
                )
            }
        }
    }
}

@Composable
private fun elevatedCardBorderColorLerp(fraction: Float) =
    lerp(
        Color.Transparent,
        MaterialTheme.colorScheme.primary,
        FastOutSlowInEasing.transform(fraction),
    )

@Composable
private fun elevatedCardBorderSizeLerp(fraction: Float) =
    lerp(
        0.dp,
        2.dp,
        FastOutLinearInEasing.transform(fraction),
    )

@Composable
private fun outlinedCardBorderColorLerp(fraction: Float) =
    lerp(
        MaterialTheme.colorScheme.outline,
        MaterialTheme.colorScheme.primary,
        FastOutSlowInEasing.transform(fraction),
    )

@Composable
private fun outlinedCardBorderSizeLerp(fraction: Float) =
    lerp(
        1.dp,
        2.dp,
        FastOutLinearInEasing.transform(fraction),
    )
