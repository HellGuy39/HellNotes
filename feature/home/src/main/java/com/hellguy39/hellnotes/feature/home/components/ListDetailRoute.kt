package com.hellguy39.hellnotes.feature.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListDetailRoute(
    modifier: Modifier = Modifier,
    isExpandedWindowsSize: Boolean,
    listContent: @Composable () -> Unit,
    detailContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        listContent()
        detailContent()
    }
}