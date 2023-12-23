package com.hellguy39.hellnotes.feature.changelog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.changelog.component.ChangelogCard

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
                                Modifier.fillMaxWidth(),
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
