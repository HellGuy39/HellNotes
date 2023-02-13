package com.hellguy39.hellnotes.core.data.repository

import com.hellguy39.hellnotes.core.datastore.HellNotesPreferencesDataSource
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.model.util.Sorting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataSource: HellNotesPreferencesDataSource
) : DataStoreRepository {

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataSource.saveOnBoardingState(completed = completed)
    }

    override suspend fun saveListStyleState(listStyle: ListStyle) {
        dataSource.saveListStyleState(style = listStyle)
    }

    override suspend fun saveListSortState(sorting: Sorting) {
        dataSource.saveListSortState(sorting = sorting)
    }

    override suspend fun saveAppSettings(appSettings: AppSettings) {
        dataSource.saveAppSettings(appSettings)
    }

    override suspend fun saveIsUseBiometricData(isUseBiometricData: Boolean) {
        dataSource.saveIsUseBiometricData(isUseBiometricData = isUseBiometricData)
    }

    override suspend fun saveAppCode(code: String) {
        dataSource.saveAppCode(code)
    }

    override suspend fun saveTrashTipChecked(isChecked: Boolean) {
        dataSource.saveOnTrashTipChecked(isChecked)
    }

    override suspend fun saveAppLockType(lockScreenType: LockScreenType) {
        dataSource.saveAppLockType(lockScreenType)
    }

    override fun readAppSettings(): Flow<AppSettings> {
        return dataSource.readAppSettings()
    }

    override fun readListSortState(): Flow<Sorting> {
        return dataSource.readListSortState()
    }

    override fun readListStyleState(): Flow<ListStyle> {
        return dataSource.readListStyleState()
    }

}