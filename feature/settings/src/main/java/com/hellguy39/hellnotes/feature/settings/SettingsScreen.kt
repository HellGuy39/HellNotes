package com.hellguy39.hellnotes.feature.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: SettingsUiState,
    selection: SettingsScreenSelection,
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            SettingsScreenContent(
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                uiState = uiState,
                selection = selection,
            )
        },
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.Settings),
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen2(
    onNavigationButtonClick: () -> Unit,
    onNavigateToGeneral: () -> Unit,
    onNavigateToStorage: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToAppearance: () -> Unit,
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
            ) {
                item {
                    ListItem(
                        modifier = Modifier.clickable { onNavigateToGeneral() },
                        headlineContent = {
                            Text(text = "General")
                        },
                        supportingContent = {
                            Text(text = "Language, gestures")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = AppIcons.Settings),
                                contentDescription = null,
                            )
                        },
                    )
                }
                item {
                    ListItem(
                        modifier = Modifier.clickable { onNavigateToStorage() },
                        headlineContent = {
                            Text(text = "Data & Storage")
                        },
                        supportingContent = {
                            Text(text = "Backup, reset")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = AppIcons.Storage),
                                contentDescription = null,
                            )
                        },
                    )
                }
                item {
                    ListItem(
                        modifier = Modifier.clickable { onNavigateToSecurity() },
                        headlineContent = {
                            Text(text = "Security")
                        },
                        supportingContent = {
                            Text(text = "Lock screen, fingerprint")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = AppIcons.SecurityVerified),
                                contentDescription = null,
                            )
                        },
                    )
                }
                item {
                    ListItem(
                        modifier = Modifier.clickable { onNavigateToAppearance() },
                        headlineContent = {
                            Text(text = "Appearance")
                        },
                        supportingContent = {
                            Text(text = "Theme, note customization")
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = AppIcons.Palette),
                                contentDescription = null,
                            )
                        },
                    )
                }
            }
        },
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.Title.Settings),
            )
        },
    )
}
