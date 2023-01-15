package com.hellguy39.hellnotes.about_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.system.BackHandler
import com.hellguy39.hellnotes.about_app.components.AboutAppScreenContent
import com.hellguy39.hellnotes.about_app.components.AboutAppTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    onNavigationButtonClick: () -> Unit
) {
    BackHandler(onBack = onNavigationButtonClick)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        content = { innerPadding ->
            AboutAppScreenContent(
                innerPadding = innerPadding
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
    AboutAppScreen(
        onNavigationButtonClick = {}
    )
}