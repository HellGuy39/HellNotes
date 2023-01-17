package com.hellguy39.hellnotes.core.domain.repository

import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting

interface AppSettingsRepository {

    fun getAppSettings(): AppSettings
    fun saveAppSettings(appSettings: AppSettings)

    fun getListStyle(): ListStyle
    fun saveListStyle(listStyle: ListStyle)

    fun getListSort(): Sorting
    fun saveListSort(sorting: Sorting)

}