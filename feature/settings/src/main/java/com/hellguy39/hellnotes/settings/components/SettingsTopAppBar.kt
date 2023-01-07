package com.hellguy39.hellnotes.settings.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigationButtonClick: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = HellNotesStrings.Title.Settings),
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
        actions = {

        }
    )
}