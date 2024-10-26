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
package com.hellguy39.hellnotes.feature.labeledit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun LabelSearchItem(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onCreateLabel: (name: String) -> Boolean,
) {
    val focusManager = LocalFocusManager.current

    var isFocused by remember { mutableStateOf(false) }
    var newLabel by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Crossfade(
            isFocused,
            label = "",
        ) { isFocused ->
            if (isFocused) {
                IconButton(
                    onClick = {
                        newLabel = ""
                        focusManager.clearFocus()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Close),
                        contentDescription = null,
                    )
                }
            } else {
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.Add),
                        contentDescription = null,
                    )
                }
            }
        }
        HNClearTextField(
            value = newLabel,
            onValueChange = { newText -> newLabel = newText },
            hint = stringResource(id = AppStrings.Hint.CreateNewLabel),
            modifier =
                Modifier
                    .focusRequester(focusRequester)
                    .weight(1f)
                    .onFocusChanged { state -> isFocused = state.isFocused },
            textStyle = MaterialTheme.typography.bodyLarge,
        )
        AnimatedVisibility(isFocused) {
            IconButton(
                onClick = {
                    if (newLabel.isNotBlank()) {
                        if (onCreateLabel(newLabel)) {
                            newLabel = ""
                            focusManager.clearFocus()
                        }
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.Done),
                    contentDescription = null,
                )
            }
        }
    }

    CustomDivider(isVisible = isFocused)
}
