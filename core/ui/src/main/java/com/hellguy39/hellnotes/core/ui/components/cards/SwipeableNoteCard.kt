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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableNoteCard(
    modifier: Modifier = Modifier,
    noteStyle: NoteStyle,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    noteWrapper: NoteWrapper,
    isSelected: Boolean,
    isSwipeable: Boolean,
    onDismissed: (SwipeToDismissBoxValue) -> Boolean,
) {
    val swipeToDismissBoxState =
        rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                when (value) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        onDismissed(value)
                    }
                    SwipeToDismissBoxValue.EndToStart -> {
                        onDismissed(value)
                    }
                    else -> false
                }
            },
        )

//    if (swipeToDismissBoxState.currentValue != SwipeToDismissBoxValue.Settled) {
//        LaunchedEffect(Unit) {
//            swipeToDismissBoxState.reset()
//        }
//    }

    val visibility = if (swipeToDismissBoxState.progress == 1f) 1f else 1f - swipeToDismissBoxState.progress

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = { /* no-op */ },
        enableDismissFromEndToStart = isSwipeable,
        enableDismissFromStartToEnd = isSwipeable,
    ) {
        NoteCard(
            modifier = modifier.alpha(visibility),
            onClick = onClick,
            onLongClick = onLongClick,
            noteWrapper = noteWrapper,
            isSelected = isSelected,
            noteStyle = noteStyle,
        )
    }
}

// @OptIn(ExperimentalMaterial3Api::class)
// @Composable
// fun SwipeableNoteBackground(
//    state: SwipeToDismissBoxState,
// ) {
//    val visibility by animateFloatAsState(targetValue = if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) 1f else 0f)
//
//    Box(
//        modifier =
//            Modifier
//                .fillMaxSize()
//                .alpha(visibility)
//                .padding(16.dp),
//        contentAlignment = Alignment.CenterEnd,
//    ) {
//        Icon(
//            painter = painterResource(id = AppIcons.Delete),
//            contentDescription = null,
//        )
//    }
// }
