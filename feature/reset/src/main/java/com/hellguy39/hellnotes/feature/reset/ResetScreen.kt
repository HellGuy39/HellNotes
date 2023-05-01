package com.hellguy39.hellnotes.feature.reset

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.items.HNCheckboxItem
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetScreen(
    onNavigationButtonClick: () -> Unit,
    onReset: (resetDatabase: Boolean, resetSettings: Boolean) -> Unit
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    var isResetDatabase by rememberSaveable { mutableStateOf(false) }
    var isResetSettings by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HNTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Reset)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(192.dp),
                    painter = painterResource(id = HellNotesIcons.RestartAlt),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = stringResource(id = HellNotesStrings.Subtitle.SelectActions),
                    style = MaterialTheme.typography.titleLarge,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    HNCheckboxItem(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        onClick = { isResetDatabase = !isResetDatabase },
                        checked = isResetDatabase,
                        title = stringResource(id = HellNotesStrings.Checkbox.ClearDatabase)
                    )

                    HNCheckboxItem(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        onClick = { isResetSettings = !isResetSettings },
                        title = stringResource(id = HellNotesStrings.Checkbox.ResetSettings),
                        checked = isResetSettings,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    onClick = { onReset(isResetDatabase, isResetSettings) },
                    enabled = isResetSettings || isResetDatabase,
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Reset),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    )
}