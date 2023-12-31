package com.hellguy39.hellnotes.feature.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.core.ui.values.Elevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    onNavigationButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    query: String,
    onQueryChanged: (newQuery: String) -> Unit,
    onClearQuery: () -> Unit,
    focusRequester: FocusRequester,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(vertical = 0.dp),
                onClick = onNavigationButtonClick,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.ArrowBack),
                    contentDescription = null,
                )
            }
        },
        title = {
            HNClearTextField(
                value = query,
                hint = stringResource(id = AppStrings.Hint.Search),
                onValueChange = onQueryChanged,
                modifier =
                    Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
            )
        },
        actions = {
            AnimatedVisibility(visible = query.isNotEmpty()) {
                IconButton(
                    modifier = Modifier,
                    onClick = onClearQuery,
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Close),
                        contentDescription = null,
                    )
                }
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor =
                    MaterialTheme.colorScheme
                        .surfaceColorAtElevation(Elevation.Level2),
                scrolledContainerColor =
                    MaterialTheme.colorScheme
                        .surfaceColorAtElevation(Elevation.Level2),
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchTopAppBarPreview() {
    HellNotesTheme {
        SearchTopAppBar(
            onNavigationButtonClick = {},
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            query = "",
            onQueryChanged = {},
            focusRequester = FocusRequester(),
            onClearQuery = {},
        )
    }
}
