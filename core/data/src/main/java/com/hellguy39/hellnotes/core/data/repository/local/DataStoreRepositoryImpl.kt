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
package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.datastore.HellNotesPreferencesDataSource
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl
    @Inject
    constructor(
        private val dataSource: HellNotesPreferencesDataSource,
    ) : DataStoreRepository {
        override fun readNoteSwipesState(): Flow<NoteSwipesState> {
            return dataSource.readNoteSwipesState()
        }

        override suspend fun saveNoteSwipesState(state: NoteSwipesState) {
            dataSource.saveNoteSwipesState(state)
        }

        override suspend fun saveOnBoardingState(completed: Boolean) {
            dataSource.saveOnBoardingState(completed = completed)
        }

        override suspend fun saveListStyleState(listStyle: ListStyle) {
            dataSource.saveListStyleState(style = listStyle)
        }

        override suspend fun saveNoteStyleState(noteStyle: NoteStyle) {
            dataSource.saveNoteStyleState(noteStyle = noteStyle)
        }

        override suspend fun saveSecurityState(securityState: SecurityState) {
            dataSource.saveSecurityState(securityState)
        }

        override suspend fun saveTrashTipState(completed: Boolean) {
            dataSource.saveTrashTipState(completed)
        }

        override suspend fun saveLastBackupDate(millis: Long) {
            dataSource.saveLastBackupDate(millis)
        }

        override fun readOnBoardingState(): Flow<Boolean> {
            return dataSource.readOnBoardingState()
        }

        override fun readTrashTipState(): Flow<Boolean> {
            return dataSource.readTrashTipState()
        }

        override fun readSecurityState(): Flow<SecurityState> {
            return dataSource.readSecurityState()
        }

        override fun readListStyleState(): Flow<ListStyle> {
            return dataSource.readListStyleState()
        }

        override fun readNoteStyleState(): Flow<NoteStyle> {
            return dataSource.readNoteStyleState()
        }

        override fun readLastBackupDate(): Flow<Long> {
            return dataSource.readLastBackupDate()
        }

        override suspend fun resetToDefault() {
            dataSource.resetToDefault()
        }
    }
