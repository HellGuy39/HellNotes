package com.hellguy39.hellnotes.core.domain.repository

import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveListStyleState(listStyle: ListStyle)

    suspend fun saveListSortState(sorting: Sorting)

    suspend fun saveNoteStyleState(noteStyle: NoteStyle)

    suspend fun saveAppSettings(appSettings: AppSettings)

    suspend fun saveIsUseBiometricData(isUseBiometricData: Boolean)

    suspend fun saveAppCode(code: String)

    suspend fun saveTrashTipChecked(isChecked: Boolean)

    suspend fun saveAppLockType(lockScreenType: LockScreenType)

    fun readAppSettings(): Flow<AppSettings>

    fun readListSortState(): Flow<Sorting>

    fun readListStyleState(): Flow<ListStyle>

    fun readNoteStyleState(): Flow<NoteStyle>

}