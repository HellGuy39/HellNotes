package com.hellguy39.hellnotes.feature.termsandconditions

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
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: TermsAndConditionsUiState,
    onTryAgain: () -> Unit,
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

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
                    title = stringResource(id = AppStrings.Title.TermsAndConditions),
                    actions = {
                        if (!uiState.isLoading && !uiState.isError) {
                            FilterChip(
                                modifier =
                                    Modifier
                                        .height(FilterChipDefaults.Height)
                                        .padding(horizontal = 12.dp),
                                selected = false,
                                onClick = {},
                                label = { Text(text = stringResource(id = AppStrings.Chip.Markdown)) },
                            )
                        }
                    },
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
                    modifier =
                        Modifier
                            .fillMaxSize(),
                    contentPadding = paddingValues,
                    content = {
                        item {
                            Material3RichText(
                                modifier = Modifier.padding(16.dp),
                            ) {
                                Markdown(
                                    content = uiState.termsAndConditions,
                                )
                            }
                        }
                    },
                )
            } else if (uiState.isError) {
                EmptyContentPlaceholder(
                    modifier =
                        Modifier
                            .padding(paddingValues)
                            .padding(horizontal = 32.dp)
                            .fillMaxSize(),
                    heroIcon = painterResource(id = AppIcons.Error),
                    message = stringResource(id = AppStrings.Placeholder.FailedToLoadData),
                    actions = {
                        TextButton(
                            onClick = onTryAgain,
                        ) {
                            Text(text = stringResource(id = AppStrings.Button.TryAgain))
                        }
                    },
                )
            }
        },
    )
}
