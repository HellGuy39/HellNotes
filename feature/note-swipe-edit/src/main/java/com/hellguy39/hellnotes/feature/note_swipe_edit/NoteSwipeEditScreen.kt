package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.NoteSwipe
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.CustomRadioButton
import com.hellguy39.hellnotes.core.ui.components.CustomSwitch
import com.hellguy39.hellnotes.core.ui.components.items.ItemHeader
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.getDisplayName
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteSwipeEditScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: NoteSwipeEditScreenUiState,
    selection: NoteSwipeEditScreenSelection
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.NoteSwipe)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(space = 16.dp)
            ) {
                item {
                    val fraction = if (uiState.noteSwipesState.enabled) 1f else 0f
                    val containerColor by animateColorAsState(
                        targetValue = lerp(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.primaryContainer,
                            FastOutSlowInEasing.transform(fraction)
                        ),
                        animationSpec = tween(200)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor)
                    ) {
                        val isChecked = uiState.noteSwipesState.enabled
                        Box(
                            modifier = Modifier
                                .selectable(
                                    selected = isChecked,
                                    onClick = { selection.onNoteSwipesEnabled(!isChecked) },
                                    role = Role.Switch
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        vertical = 24.dp,
                                        horizontal = 24.dp
                                    )
                            ) {
                                CustomSwitch(
                                    modifier = Modifier.fillMaxWidth(),
                                    title = stringResource(id = HellNotesStrings.Switch.UseNoteSwipes),
                                    checked = isChecked,
                                )
                            }
                        }
                    }
                }
                item {
                    ItemHeader(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = stringResource(id = HellNotesStrings.Label.SwipeLeft),
                        icon = painterResource(id = HellNotesIcons.SwipeLeft)
                    )

                    Column(
                        modifier = Modifier.selectableGroup()
                    ) {
                        NoteSwipe.actions.forEach { swipeAction ->
                            val isSelected = uiState.noteSwipesState.swipeLeft == swipeAction
                            CustomRadioButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(UiDefaults.ListItem.DefaultHeight)
                                    .selectable(
                                        selected = isSelected,
                                        onClick = {
                                            selection.onSwipeLeftActionSelected(swipeAction)
                                        },
                                        enabled = uiState.noteSwipesState.enabled,
                                        role = Role.RadioButton
                                    ),
                                isSelected = isSelected,
                                title = swipeAction.getDisplayName(),
                                enabled = uiState.noteSwipesState.enabled,
                            )
                        }
                    }
                }
                item {
                    ItemHeader(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        title = stringResource(id = HellNotesStrings.Label.SwipeRight),
                        icon = painterResource(id = HellNotesIcons.SwipeRight)
                    )
                    Column(
                        modifier = Modifier.selectableGroup()
                    ) {
                        NoteSwipe.actions.forEach { swipeAction ->
                            val isSelected = uiState.noteSwipesState.swipeRight == swipeAction
                            CustomRadioButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(UiDefaults.ListItem.DefaultHeight)
                                    .selectable(
                                        selected = isSelected,
                                        onClick = {
                                            selection.onSwipeRightActionSelected(swipeAction)
                                        },
                                        enabled = uiState.noteSwipesState.enabled,
                                        role = Role.RadioButton
                                    ),
                                isSelected = isSelected,
                                title = swipeAction.getDisplayName(),
                                enabled = uiState.noteSwipesState.enabled,
                            )
                        }
                    }
                }
            }
        }
    )
}

data class NoteSwipeEditScreenSelection(
    val onNoteSwipesEnabled: (Boolean) -> Unit,
    val onSwipeRightActionSelected: (NoteSwipe) -> Unit,
    val onSwipeLeftActionSelected: (NoteSwipe) -> Unit
)