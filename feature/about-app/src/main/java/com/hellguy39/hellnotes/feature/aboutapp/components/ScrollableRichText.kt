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
package com.hellguy39.hellnotes.feature.aboutapp.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
internal fun ScrollableRichText(
    modifier: Modifier = Modifier,
    content: String,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        content = {
            item {
                Material3RichText(
                    modifier = Modifier.padding(Spaces.medium),
                ) {
                    Markdown(
                        content = content,
                    )
                }
            }
        },
    )
}
