package com.hellguy39.hellnotes.core.data.repository

import android.content.Context
import androidx.core.content.edit
import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
): AppSettingsRepository {

    private val prefs = context.getSharedPreferences(PREFS_UI_STATE, Context.MODE_PRIVATE)

    override fun getAppSettings(): AppSettings {
        val appPin = prefs.getString(ARG_APP_PIN, "").toString()
        val isPinSetup = prefs.getBoolean(ARG_IS_PIN_SETUP, false)
        val isAppUseBiometric = prefs.getBoolean(ARG_IS_USE_BIOMETRIC, false)

        return AppSettings(
            isPinSetup = isPinSetup,
            isUseBiometric = isAppUseBiometric,
            appPin = appPin,
        )
    }

    override fun saveAppSettings(appSettings: AppSettings) {
        prefs.edit {
            putString(ARG_APP_PIN, appSettings.appPin)
            putBoolean(ARG_IS_PIN_SETUP, appSettings.isPinSetup)
            putBoolean(ARG_IS_USE_BIOMETRIC, appSettings.isUseBiometric)
            commit()
        }
    }

    override fun getListStyle(): ListStyle {
        val listStyle = prefs.getString(
            ARG_LIST_STYLE,
            ListStyle.COLUMN
        ).toString()
        return ListStyle.from(listStyle)
    }

    override fun saveListStyle(listStyle: ListStyle) {
        prefs.edit {
            putString(ARG_LIST_STYLE, listStyle.parse())
            commit()
        }
    }

    override fun getListSort(): Sorting {
        val sorting = prefs.getString(
            ARG_SORTING,
            Sorting.DATE_OF_CREATION
        ).toString()
        return Sorting.from(sorting)
    }

    override fun saveListSort(sorting: Sorting) {
        prefs.edit {
            putString(ARG_SORTING, sorting.parse())
            commit()
        }
    }

    companion object {
        private const val PREFS_UI_STATE = "prefs_ui_state"

        private const val ARG_LIST_STYLE = "arg_list_style"
        private const val ARG_SORTING = "arg_sorting"

        private const val ARG_IS_PIN_SETUP = "arg_is_pin_setup"
        private const val ARG_IS_USE_BIOMETRIC = "arg_is_use_biometric"
        private const val ARG_APP_PIN = "arg_app_pin"
    }
}