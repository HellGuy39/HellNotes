package com.hellguy39.hellnotes.feature.home.reminders.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: ReminderTopAppBarSelection
) {
    val listStyleIcon = if(selection.listStyle == ListStyle.Column)
        painterResource(id = HellNotesIcons.GridView)
    else
        painterResource(id = HellNotesIcons.ListView)

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
                onClick = { selection.onNavigation() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Menu),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = { selection.onChangeListStyle() }
            ) {
                Icon(
                    painter = listStyleIcon,
                    contentDescription = null
                )
            }
        }
    )
}

data class ReminderTopAppBarSelection(
    val listStyle: ListStyle,
    val onNavigation: () -> Unit,
    val onChangeListStyle: () -> Unit
)