package com.hellguy39.hellnotes.core.data.repository

import com.hellguy39.hellnotes.core.datastore.HellNotesPreferencesDataSource
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.NoteSwipesState
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataSource: HellNotesPreferencesDataSource
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

    override suspend fun saveListSortState(sorting: Sorting) {
        dataSource.saveListSortState(sorting = sorting)
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

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataSource.readOnBoardingState()
    }

    override fun readTrashTipState(): Flow<Boolean> {
        return dataSource.readTrashTipState()
    }

    override fun readSecurityState(): Flow<SecurityState> {
        return dataSource.readSecurityState()
    }

    override fun readListSortState(): Flow<Sorting> {
        return dataSource.readListSortState()
    }

    override fun readListStyleState(): Flow<ListStyle> {
        return dataSource.readListStyleState()
    }

    override fun readNoteStyleState(): Flow<NoteStyle> {
        return dataSource.readNoteStyleState()
    }

}