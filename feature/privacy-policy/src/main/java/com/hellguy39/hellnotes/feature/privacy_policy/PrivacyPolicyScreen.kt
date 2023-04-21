package com.hellguy39.hellnotes.feature.privacy_policy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: PrivacyPolicyUiState,
    onTryAgain: () -> Unit
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
                    title = stringResource(id = HellNotesStrings.Title.PrivacyPolicy),
                    actions = {
                        if (!uiState.isLoading && !uiState.isError) {
                            FilterChip(
                                modifier = Modifier
                                    .height(FilterChipDefaults.Height)
                                    .padding(horizontal = 12.dp),
                                selected = false,
                                onClick = {
                                },
                                label = { Text(text = stringResource(id = HellNotesStrings.Markdown)) },
                            )
                        }
                    }
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        content = { paddingValues ->
            if (!uiState.isLoading && !uiState.isError) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = paddingValues,
                    content = {
                        item {
                            Material3RichText(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Markdown(
                                    content = uiState.privacyPolicy
                                )
                            }
                        }
                    }
                )
            } else if (uiState.isError) {
                EmptyContentPlaceholder(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 32.dp)
                        .fillMaxSize(),
                    heroIcon = painterResource(id = HellNotesIcons.Error),
                    message = stringResource(id = HellNotesStrings.Helper.FailedToLoadData),
                    actions = {
                        TextButton(
                            onClick = onTryAgain
                        ) {
                            Text(text = stringResource(id = HellNotesStrings.Button.TryAgain))
                        }
                    }
                )
            }
        }
    )
}