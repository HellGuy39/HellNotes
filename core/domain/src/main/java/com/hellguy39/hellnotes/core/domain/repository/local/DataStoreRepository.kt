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
