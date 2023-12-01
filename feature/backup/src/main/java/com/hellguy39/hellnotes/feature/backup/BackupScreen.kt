package com.hellguy39.hellnotes.feature.backup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.UiText
import com.hellguy39.hellnotes.core.ui.components.items.HNSwitchItem
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.snack.getSnackMessage
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.IconSize
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: BackupUiState,
    onBackupClick: () -> Unit,
    onRestoreClick: () -> Unit
) {
    val context = LocalContext.current
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.message) {
        uiState.message.let { message ->
            if (message !is UiText.Empty) {
                snackbarHostState.showDismissableSnackbar(
                    scope = scope,
                    message = message.asString(context),
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HNLargeTopAppBar(
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
        bottomBar = {
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(horizontal = Spaces.medium, vertical = Spaces.small),
                horizontalArrangement = Arrangement.spacedBy(Spaces.medium),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    modifier = Modifier,
                    onClick = onRestoreClick
                ) {
                    Text(text = stringResource(id = HellNotesStrings.Button.Restore))
                }
                Button(
                    modifier = Modifier,
                    onClick = onBackupClick
                ) {
                    Text(text = stringResource(id = HellNotesStrings.Button.Create))
                }
            }
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = Spaces.large,
                alignment = Alignment.Top
            ),
            contentPadding = paddingValues
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = Spaces.medium)
                ) {
                    Text(
                        text = "Last copy: November 29, 2023 (10:22)",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            item {
                ElevatedCard(
                    modifier = Modifier.padding(horizontal = Spaces.medium)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spaces.medium),
                        verticalArrangement = Arrangement.spacedBy(Spaces.medium)
                    ) {
                        // TODO: set accented icon tint & maybe create separate composable item
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Info),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(Spaces.small)
                        ) {
                            Text(
                                text = "Attention",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = stringResource(id = HellNotesStrings.Supporting.Backup),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            role = Role.Switch,
                            onClick = {},
                            selected = true
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = Spaces.medium, vertical = Spaces.medium),
                        horizontalArrangement = Arrangement.spacedBy(Spaces.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(Spaces.small)
                        ) {
                            Text(
                                text = "Automatic backup",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Every day, in the background",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(DividerDefaults.Thickness)
                        )
                        Switch(
                            checked = true,
                            onCheckedChange = null,
                            enabled = true
                        )
                    }
                }
            }
        }
    }
}