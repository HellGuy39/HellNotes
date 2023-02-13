package com.hellguy39.hellnotes.feature.about_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.about_app.components.AboutAppScreenContent
import com.hellguy39.hellnotes.feature.about_app.components.AboutAppTopAppBar
import com.hellguy39.hellnotes.ui.theme.HellNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    onNavigationButtonClick: () -> Unit,
    onEasterEgg: () -> Unit
) {
    BackHandler(onBack = onNavigationButtonClick)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        content = { innerPadding ->
            AboutAppScreenContent(
                innerPadding = innerPadding,
                onEasterEgg = onEasterEgg
            )
        },
        topBar = {
            AboutAppTopAppBar(
                onNavigationButtonClick = onNavigationButtonClick
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AboutAppScreenPreview() {
    HellNotesTheme {
        AboutAppScreen(
            onNavigationButtonClick = {},
            onEasterEgg = {}
        )
    }
}