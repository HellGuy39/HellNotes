package com.hellguy39.hellnotes.feature.changelog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: ChangelogUiState,
    onTryAgain: () -> Unit,
    onOpenRelease: (release: Release) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                HNTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onNavigationButtonClick = onNavigationButtonClick,
                    title = stringResource(id = HellNotesStrings.Title.Changelog),
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        content = { innerPadding ->
            if (!uiState.isError && !uiState.isLoading) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding =
                        PaddingValues(
                            top = innerPadding.calculateTopPadding() + Spaces.medium,
                            bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
                            start = Spaces.medium,
                            end = Spaces.medium,
                        ),
                    verticalArrangement = Arrangement.spacedBy(Spaces.medium),
                ) {
                    items(
                        items = uiState.releases,
                        key = { item -> item.id ?: 0 },
                    ) { release ->
                        ChangelogCard(
                            modifier =
                                Modifier
                                    .fillMaxWidth(),
                            // .animateItemPlacement(),
                            release = release,
                            onOpenReleaseClick = { onOpenRelease(release) },
                        )
                    }
                }
            } else if (uiState.isError) {
                EmptyContentPlaceholder(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .padding(horizontal = Spaces.extraLarge)
                            .fillMaxSize(),
                    heroIcon = painterResource(id = HellNotesIcons.Error),
                    message = stringResource(id = HellNotesStrings.Placeholder.FailedToLoadData),
                    actions = {
                        TextButton(
                            onClick = onTryAgain,
                        ) {
                            Text(text = stringResource(id = HellNotesStrings.Button.TryAgain))
                        }
                    },
                )
            }
        },
    )
}

@Composable
fun ChangelogCard(
    modifier: Modifier = Modifier,
    release: Release,
    onOpenReleaseClick: () -> Unit,
) {
    val localDateTime = DateTimeUtils.iso8061toLocalDateTime(release.publishedAt ?: "")
    val date = DateTimeUtils.formatLocalDateTime(localDateTime, DateTimeUtils.DATE_TIME_PATTERN)

    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Spaces.medium),
            verticalArrangement = Arrangement.spacedBy(Spaces.medium),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(Spaces.small),
                ) {
                    Text(
                        text = release.tagName ?: "",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                IconButton(onClick = { isExpanded = isExpanded.not() }) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.expand(isExpanded)),
                        contentDescription = null,
                    )
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Spaces.medium),
                ) {
                    Divider()
                    Text(
                        text = release.body ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Divider()
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    onClick = onOpenReleaseClick,
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        painter = painterResource(id = HellNotesIcons.ArrowOutward),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = HellNotesStrings.Button.OpenARelease))
                }
            }
        }
    }
}
