package com.hellguy39.hellnotes.feature.about_app.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppTopAppBar(
    onNavigationButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier,
                text = stringResource(id = HellNotesStrings.Title.AboutApp),
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