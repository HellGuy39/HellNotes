package com.hellguy39.hellnotes.about_app.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppTopAppBar(
    onNavigationButtonClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = HellNotesStrings.Title.AboutApp)) },
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