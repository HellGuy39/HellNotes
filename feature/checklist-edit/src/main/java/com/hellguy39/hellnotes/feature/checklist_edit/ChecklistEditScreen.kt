package com.hellguy39.hellnotes.feature.checklist_edit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Checklist
import com.hellguy39.hellnotes.core.model.ChecklistItem
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItemDefault
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChecklistEditScreen(
    onNavigation: () -> Unit,
    checklist: Checklist,
    selection: ChecklistEditScreenSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigation
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier,
                contentPadding = paddingValues
            ) {
                items(
                    items = checklist.items
                ) { item ->

                    var isFocused by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp),
                                    painter = painterResource(id = HellNotesIcons.DragHandle),
                                    contentDescription = null
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            CustomTextField(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .weight(1f)
                                    .onFocusChanged { state -> isFocused = state.isFocused },
                                value = item.text,
                                onValueChange = { text -> selection.onTextChange(text, item) },
                                isSingleLine = false,
                            )

                            if (isFocused) {
                                IconButton(
                                    onClick = { selection.onDelete(item) },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = HellNotesIcons.Delete),
                                        contentDescription = null
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Spacer(modifier = Modifier.fillMaxSize())
                                }
                            }
                        }
                    }
                    CustomDivider(isVisible = isFocused)
                }
                item {
                    SelectionItemDefault(
                        heroIcon = painterResource(id = HellNotesIcons.Add),
                        title = "Add new item",
                        onClick = selection.onAdd
                    )
                }
            }
        }
    )
}

data class ChecklistEditScreenSelection(
    val onAdd: () -> Unit,
    val onDelete: (ChecklistItem) -> Unit,
    val onMove: (Int, Int) -> Unit,
    val onTextChange: (String, ChecklistItem) -> Unit
)