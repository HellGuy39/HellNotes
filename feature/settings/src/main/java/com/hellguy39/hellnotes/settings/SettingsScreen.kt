package com.hellguy39.hellnotes.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.settings.util.Language
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    //uiState: UiState,
    onNavigationButtonClick: () -> Unit,
    languageDialogEvents: LanguageDialogEvents,
    isShowLanguageDialog: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    BackHandler(onBack = onNavigationButtonClick)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            LanguageDialog(
                isShowDialog = isShowLanguageDialog,
                events = languageDialogEvents,
            )
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                item {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        onClick = {
                            languageDialogEvents.show()
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(id = HellNotesStrings.Text.Language),
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = Language.getFullName(code = languageDialogEvents.getCurrentLanCode()),
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        },
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Text.Settings),
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
        }
    )
}

@Composable
fun LanguageDialog(
    isShowDialog: Boolean,
    events: LanguageDialogEvents
) {
    CustomDialog(
        showDialog = isShowDialog,
        onClose = { events.dismiss() },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                //.fillMaxWidth()
                .padding(innerPadding),
        ) {
            items(Language.languageCodes) {
                Row(
                    modifier = Modifier
                        .clickable {
                            events.onLanguageSelected(it)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val isSelected = it == events.getCurrentLanCode()

                    Spacer(modifier = Modifier.width(16.dp))

                    if (isSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Done),
                            contentDescription = null
                        )
                    } else {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                    Text(
                        text = Language.getFullName(code = it),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )   
                }
            }
        }
    }
}