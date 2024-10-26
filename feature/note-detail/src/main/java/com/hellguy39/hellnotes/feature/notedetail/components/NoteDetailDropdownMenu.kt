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
package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItemSelection
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun NoteDetailDropdownMenu(
    state: CustomDropdownMenuState,
    selection: NoteDetailDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() },
        items =
            listOf(
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = AppIcons.Share),
                    text = stringResource(id = AppStrings.MenuItem.Share),
                    onClick = {
                        state.dismiss()
                        selection.onShare()
                    },
                ),
                CustomDropdownItemSelection(
                    leadingIconId = painterResource(id = AppIcons.Delete),
                    text = stringResource(id = AppStrings.MenuItem.Delete),
                    onClick = {
                        state.dismiss()
                        selection.onDelete()
                    },
                ),
            ),
    )
}

data class NoteDetailDropdownMenuSelection(
    val onShare: () -> Unit,
    val onDelete: () -> Unit,
)
