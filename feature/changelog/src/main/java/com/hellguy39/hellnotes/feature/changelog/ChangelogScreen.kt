package com.hellguy39.hellnotes.feature.changelog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogScreen(
    onNavigationButtonClick: () -> Unit
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Changelog)
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = paddingValues,
                content = {
                    items(
                        items = changelogs,
                        key = { item -> item.id }
                    ) { selection ->
                        ChangelogCard(
                            modifier = Modifier.fillMaxWidth(),
                            selection = selection
                        )
                    }
                }
            )
        }
    )
}

private val changelogs = listOf(
    ChangelogItemSelection(
        id = 6,
        version = "1.0.0",
        date = "17/04/2023",
        changelog = """
            • Introducing checklists
            • On boarding process
            • Password as a way to lock the application
            • Reset app feature
            • Support for French and German languages
            • Visuals improvements of "Note Style" screen
            • Visuals improvements of "Search" screen
            • Rename and delete label directly from the "Home Screen"
            • Improved performance of "Note Edit" screen
            • Full refactoring of "About app" screen
            • Crash Screen
            • Other bug fixes & visual improvements
        """.trimIndent()
    ),
    ChangelogItemSelection(
        id = 5,
        version = "1.0.0-rc03",
        date = "14/03/2023",
        changelog = """
            • Note & Snackbar Swipes
            • Note Styles (Outlined & Elevated)
            • New dialogs
            • Repeating reminders
            • Reminders & Labels dialogs are now displayed on separate screens
            • Bug fixes & visual improvements
            • Support predictive back gesture (Android 14)
        """.trimIndent()
    ),
    ChangelogItemSelection(
        id = 4,
        version = "1.0.0-rc02",
        date = "16/02/2023",
        changelog = """
            • Fix crash on MIUI & EMUI
            • A few visual improvements
        """.trimIndent()
    ),
    ChangelogItemSelection(
        id = 3,
        version = "1.0.0-rc01",
        date = "13/02/2023",
        changelog = """
            • Full refactoring of settings
            • New shortcuts - Archive & Trash
            • Auto delete notes in trash after 7 days
            • Fix grid display
            • Fix icon sizes & text style
            • Other small changes & improvements
        """.trimIndent()
    ),
    ChangelogItemSelection(
        id = 2,
        version = "1.0.0-beta02",
        date = "4/02/2023",
        changelog = """
            • Navigation drawer
            • Update animations
            • Trash & Archive
            • Labels edit screen
            • Other small changes & improvements
        """.trimIndent()
    ),
    ChangelogItemSelection(
        id = 1,
        version = "1.0.0-beta01",
        date = "18/01/2023",
        changelog = "Initial release."
    )
)

data class ChangelogItemSelection(
    val id: Long,
    val version: String,
    val date: String,
    val changelog: String
)

@Composable
fun ChangelogCard(
    modifier: Modifier = Modifier,
    selection: ChangelogItemSelection
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selection.version,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = selection.date,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = selection.changelog,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth()
    )
}