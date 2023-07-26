package com.hellguy39.hellnotes.feature.settings.detail.security

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.component.items.HNListItem
import com.hellguy39.hellnotes.core.ui.component.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.component.top_bar.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen() {
    Scaffold(
        topBar = {
            HNTopAppBar(title = "Security")
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
                        title = stringResource(id = HellNotesStrings.Setting.ScreenLock),
                        subtitle = "-lock_type-",
                    )
                }
                item {
                    HNSwitchItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(id = HellNotesStrings.Switch.UseBiometric),
                        checked = true,
                        enabled = true,
                        onClick = {},
                    )
                }
            }
        }
    )
}