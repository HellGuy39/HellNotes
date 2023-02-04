package com.hellguy39.hellnotes.feature.search.components

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
import com.hellguy39.hellnotes.core.ui.components.CustomTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    onNavigationButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    query: String,
    onQueryChanged: (newQuery: String) -> Unit,
    focusRequester: FocusRequester
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            ElevatedCard(
                onClick = {  },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = { onNavigationButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.ArrowBack),
                            contentDescription = null
                        )
                    }
                    CustomTextField(
                        value = query,
                        hint = stringResource(id = HellNotesStrings.Hint.Search),
                        onValueChange = { newText -> onQueryChanged(newText) },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .weight(1f),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        },
        navigationIcon = {},
        actions = {
            Spacer(modifier = Modifier.width(16.dp))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchTopAppBarPreview() {
    SearchTopAppBar(
        onNavigationButtonClick = { },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        query = "",
        onQueryChanged = {},
        focusRequester = FocusRequester()
    )
}