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
package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import kotlinx.coroutines.flow.StateFlow

interface NoteActionController {
    val items: StateFlow<List<Long>>

    fun select(noteId: Long)

    fun unselect(noteId: Long)

    fun cancel()

    suspend fun undo()

    suspend fun moveToTrash()

    suspend fun deleteForever()

    suspend fun restoreFromTrash()

    suspend fun archive(isArchived: Boolean)

    suspend fun handleSwipe(noteSwipe: NoteSwipe, noteId: Long)

    sealed class Action {
        data object Delete : Action()

        data object Archive : Action()

        data object Unarchive : Action()

        data object Empty : Action()
    }
}
