package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.model.util.Sorting

interface AppSettingsRepository {

//    fun getAppSettings(): AppSettings
//    fun saveAppSettings(appSettings: AppSettings)

    fun getListStyle(): ListStyle
    fun saveListStyle(listStyle: ListStyle)

    fun getListSort(): Sorting
    fun saveListSort(sorting: Sorting)

}