package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.NoteSwipesState
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveNoteSwipesState(state: NoteSwipesState)

    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveListStyleState(listStyle: ListStyle)

    suspend fun saveListSortState(sorting: Sorting)

    suspend fun saveNoteStyleState(noteStyle: NoteStyle)

    suspend fun saveSecurityState(securityState: SecurityState)

    suspend fun saveTrashTipState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>

    fun readTrashTipState(): Flow<Boolean>

    fun readSecurityState(): Flow<SecurityState>

    fun readNoteSwipesState(): Flow<NoteSwipesState>

    fun readListSortState(): Flow<Sorting>

    fun readListStyleState(): Flow<ListStyle>

    fun readNoteStyleState(): Flow<NoteStyle>

    suspend fun resetToDefault()

}