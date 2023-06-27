package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.local.datastore.SecurityState
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.local.datastore.Sorting
import com.hellguy39.hellnotes.core.model.local.datastore.ThemeState
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveNoteSwipesState(state: NoteSwipesState)

    suspend fun saveThemeState(state: ThemeState)

    suspend fun saveMaterialYouState(enabled: Boolean)

    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveListStyleState(listStyle: ListStyle)

    suspend fun saveListSortState(sorting: Sorting)

    suspend fun saveNoteStyleState(noteStyle: NoteStyle)

    suspend fun saveSecurityState(securityState: SecurityState)

    suspend fun saveTrashTipState(completed: Boolean)

    suspend fun saveLastBackupDate(millis: Long)

    fun readOnBoardingState(): Flow<Boolean>

    fun readThemeState(): Flow<ThemeState>

    fun readMaterialYouState(): Flow<Boolean>

    fun readTrashTipState(): Flow<Boolean>

    fun readSecurityState(): Flow<SecurityState>

    fun readNoteSwipesState(): Flow<NoteSwipesState>

    fun readListSortState(): Flow<Sorting>

    fun readListStyleState(): Flow<ListStyle>

    fun readNoteStyleState(): Flow<NoteStyle>

    fun readLastBackupDate(): Flow<Long>

    suspend fun resetToDefault()

}