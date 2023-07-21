package com.hellguy39.hellnotes.feature.settings.detail.security

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.hellguy39.hellnotes.core.ui.component.top_bar.HNTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen() {
    Scaffold(
        topBar = {
            HNTopAppBar(title = "Security")
        },
        content = { innerPadding ->

        }
    )
}