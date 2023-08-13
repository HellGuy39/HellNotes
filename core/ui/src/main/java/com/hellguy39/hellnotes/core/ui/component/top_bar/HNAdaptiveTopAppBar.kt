package com.hellguy39.hellnotes.core.ui.component.top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.window.isNotCompact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNAdaptiveTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    windowWidthSize: WindowWidthSizeClass,
) {
    HNTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        content = content,
        navigationIcon = {
            if (windowWidthSize.isNotCompact()) {
                navigationIcon()
            }
        },
        actions = actions
    )
}