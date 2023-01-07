package com.hellguy39.hellnotes.reminders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.components.ReminderCard
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    onNavigationButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    uiState: UiState
) {
    BackHandler(onBack = onNavigationButtonClick)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        stringResource(id = HellNotesStrings.Title.Reminders),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigationButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.ArrowBack),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Back)
                        )
                    }
                },
                actions = {}
            )
        },
        content = { paddingValues ->
            when(uiState) {
                is UiState.Success -> {
                    if (uiState.reminders.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                            contentPadding = paddingValues
                        ) {
                            item {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Label.Upcoming),
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            items(uiState.reminders) {
                                ReminderCard(
                                    remind = it,
                                    onClick = {  },
                                    onLongClick = {  },
                                    onDeleteClick = {},
                                    onEditClick = {}
                                )
                            }
                        }
                    } else {
                        EmptyContentPlaceholder(
                            heroIcon = painterResource(id = HellNotesIcons.Notifications),
                            message = stringResource(id = HellNotesStrings.Text.Empty)
                        )
                    }
                }
                else -> Unit
            }
        }
    )
}