package com.hellguy39.hellnotes.feature.changelog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Release
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.top_bars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChangelogScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: ChangelogUiState,
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
                    title = stringResource(id = HellNotesStrings.Title.Changelog)
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        content = { paddingValues ->
            if (!uiState.isError && !uiState.isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = paddingValues,
                    content = {
                        items(
                            items = uiState.releases,
                            key = { item -> item.id ?: 0 }
                        ) { release ->
                            ChangelogCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemPlacement(),
                                release = release
                            )
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

@Composable
fun ChangelogCard(
    modifier: Modifier = Modifier,
    release: Release
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = release.tag_name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                val localDateTime = DateTimeUtils.iso8061toLocalDateTime(release.published_at ?: "")
                val date = DateTimeUtils.formatLocalDateTime(localDateTime, DateTimeUtils.CHANGELOG_RELEASE_PATTERN)
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = release.body ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth()
    )
}