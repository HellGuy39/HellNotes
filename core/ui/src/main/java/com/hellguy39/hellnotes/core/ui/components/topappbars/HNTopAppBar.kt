/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
fun HNTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationButtonClick: () -> Unit = {},
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        scrollBehavior = scrollBehavior,
        onNavigationButtonClick = onNavigationButtonClick,
        content = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationButtonClick: () -> Unit = {},
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        scrollBehavior = scrollBehavior,
        onNavigationButtonClick = onNavigationButtonClick,
        content = content,
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SmallTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationButtonClick: () -> Unit = {},
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            content()
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
