package com.hellguy39.hellnotes.core.ui.component.top_bar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationButtonClick: () -> Unit = {},
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    @DrawableRes navigationIconId: Int? = null
) {
    SmallTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        content = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (navigationIconId != null) {
                IconButton(onClick = onNavigationButtonClick) {
                    Icon(
                        painter = painterResource(id = navigationIconId),
                        contentDescription = null
                    )
                }
            }
        },
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit
) {
    SmallTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        content = content,
        actions = actions,
        navigationIcon = navigationIcon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SmallTopAppBar(
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    navigationIcon: @Composable () -> Unit
) {
    TopAppBar(
        modifier = modifier ,
        scrollBehavior = scrollBehavior,
        title = {
            content()
        },
        navigationIcon = {
            navigationIcon()
        },
        actions = actions
    )
}