package com.hellguy39.hellnotes.feature.backup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: BackupUiState,
    selection: BackupScreenSelection,
) {

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HNTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onNavigationButtonClick = onNavigationButtonClick,
                    title = stringResource(id = HellNotesStrings.Title.Backup),
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier.size(192.dp),
                    painter = painterResource(id = HellNotesIcons.Save),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(
                        id = HellNotesStrings.Helper.LastCopy,
                        if (uiState.lastBackupDate == 0L) stringResource(id = HellNotesStrings.Helper.Never) else DateTimeUtils.formatBest(uiState.lastBackupDate)
                    ),
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = HellNotesStrings.Helper.Backup),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = selection.onBackup
                    ) {
                        Text(text = stringResource(id = HellNotesStrings.Button.CreateBackup))
                    }

                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = selection.onRestore
                    ) {
                        Text(text = stringResource(id = HellNotesStrings.Button.RestoreFromBackup))
                    }
                }
            }
        }
    )

}

data class BackupScreenSelection(
    val onBackup: () -> Unit,
    val onRestore: () -> Unit
)