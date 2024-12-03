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
package com.hellguy39.hellnotes.feature.notedetail

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.ui.components.snack.showSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.state.resources
import com.hellguy39.hellnotes.feature.notedetail.util.ShareType
import com.hellguy39.hellnotes.feature.notedetail.util.ShareUtils
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberNoteDetailState(
    context: Context = LocalContext.current,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(context, resources, coroutineScope) {
    HomeState(
        context = context,
        resources = resources,
        coroutineScope = coroutineScope
    )
}

@Stable
class HomeState(
    val context: Context,
    val resources: Resources,
    val coroutineScope: CoroutineScope,
) {
    fun shareNote(
        shareType: ShareType,
        noteWrapper: NoteWrapper,
    ) {
        ShareUtils.share(
            context = context,
            note = noteWrapper.note,
            type = shareType,
        )
    }

    fun showToast(uiText: UiText) {
        Toast.makeText(
            context,
            uiText.asString(context),
            Toast.LENGTH_SHORT,
        ).show()
    }
}
