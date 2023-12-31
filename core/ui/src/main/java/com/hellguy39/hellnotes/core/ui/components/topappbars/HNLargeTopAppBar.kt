package com.hellguy39.hellnotes.core.ui.components.topappbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNLargeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigationButtonClick: () -> Unit,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
) {
    // val isExpanded = scrollBehavior.state.collapsedFraction <= 0.5f

    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
//                maxLines = if (isExpanded) 2 else 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationButtonClick,
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.ArrowBack),
                    contentDescription = stringResource(id = AppStrings.ContentDescription.Back),
                )
            }
        },
        actions = actions,
    )
}
