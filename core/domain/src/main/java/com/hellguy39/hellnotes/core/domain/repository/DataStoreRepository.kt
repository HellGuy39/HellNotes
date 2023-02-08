package com.hellguy39.hellnotes.core.domain.repository

import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveListStyleState(listStyle: ListStyle)

    suspend fun saveListSortState(sorting: Sorting)

    suspend fun saveAppSettings(appSettings: AppSettings)

    fun readAppSettings(): Flow<AppSettings>

    fun readListSortState(): Flow<Sorting>

    fun readListStyleState(): Flow<ListStyle>

}