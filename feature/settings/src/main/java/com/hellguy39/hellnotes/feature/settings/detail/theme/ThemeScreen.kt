package com.hellguy39.hellnotes.feature.settings.detail.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.component.items.HNListItem
import com.hellguy39.hellnotes.core.ui.component.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.component.top_bar.HNTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen() {
    Scaffold(
        topBar = {
            HNTopAppBar(title = "Appearance")
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            ) {
                item {
                    HNListItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {},
                        title = "Theme",
                        subtitle = "-subtitle-",
                    )
                }
                item {
                    HNSwitchItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = "Material You",
                        checked = true,
                        enabled = true,
                        onClick = {  },
                    )
                }
            }
        }
    )
}