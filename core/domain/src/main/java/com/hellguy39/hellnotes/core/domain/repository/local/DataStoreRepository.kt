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

import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveNoteSwipesState(state: NoteSwipesState)

    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveListStyleState(listStyle: ListStyle)

    suspend fun saveNoteStyleState(noteStyle: NoteStyle)

    suspend fun saveSecurityState(securityState: SecurityState)

    suspend fun saveTrashTipState(completed: Boolean)

    suspend fun saveLastBackupDate(millis: Long)

    fun readOnBoardingState(): Flow<Boolean>

    fun readTrashTipState(): Flow<Boolean>

    fun readSecurityState(): Flow<SecurityState>

    fun readNoteSwipesState(): Flow<NoteSwipesState>

    fun readListStyleState(): Flow<ListStyle>

    fun readNoteStyleState(): Flow<NoteStyle>

    fun readLastBackupDate(): Flow<Long>

    suspend fun resetToDefault()
}
