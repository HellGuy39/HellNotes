package com.hellguy39.hellnotes.core.ui.component.search

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.hellguy39.hellnotes.core.ui.value.spacing
import com.hellguy39.hellnotes.core.ui.window.isCompact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNAdaptiveSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    horizontalPadding: Dp = MaterialTheme.spacing.none,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    windowWidthSize: WindowWidthSizeClass,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {

    val searchBarHorizontalPaddingState by animateDpAsState(
        targetValue = if (active) MaterialTheme.spacing.none else horizontalPadding,
        label = "label",
        animationSpec = tween(220)
    )

    if (windowWidthSize.isCompact()) {
        SearchBar(
            modifier = modifier.padding(horizontal = searchBarHorizontalPaddingState),
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            enabled = enabled,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            content = content,
        )
    } else {
        DockedSearchBar(
            modifier = modifier.padding(horizontal = horizontalPadding)
                .statusBarsPadding(),
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            enabled = enabled,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            content = content,
        )
    }
}