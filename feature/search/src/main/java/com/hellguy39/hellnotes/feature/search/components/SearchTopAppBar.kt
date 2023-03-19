package com.hellguy39.hellnotes.feature.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    onNavigationButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    query: String,
    onQueryChanged: (newQuery: String) -> Unit,
    onClearQuery: () -> Unit,
    focusRequester: FocusRequester
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(vertical = 0.dp),
                onClick = onNavigationButtonClick
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.ArrowBack),
                    contentDescription = null
                )
            }
        },
        title = {
            CustomTextField(
                value = query,
                hint = stringResource(id = HellNotesStrings.Hint.Search),
                onValueChange = onQueryChanged,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        },
        actions = {
            AnimatedVisibility(visible = query.isNotEmpty()) {
                IconButton(
                    modifier = Modifier,
                    onClick = onClearQuery
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Close),
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level2),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level2)
        )
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
            onClearQuery = {}
        )
    }
}