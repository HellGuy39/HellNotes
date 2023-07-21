package com.hellguy39.hellnotes.feature.settings.detail.general

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.component.top_bar.HNTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralScreen() {
    Scaffold(
        topBar = {
            HNTopAppBar(title = "General")
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            ) {
                item {

                }
            }
        }
    )
}