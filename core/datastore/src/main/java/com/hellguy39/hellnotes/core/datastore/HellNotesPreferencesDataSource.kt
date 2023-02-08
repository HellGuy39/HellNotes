package com.hellguy39.hellnotes.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hellnotes_pref")

class HellNotesPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
) {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val isPinSetup = booleanPreferencesKey(name = "is_pin_setup")
        val isBiometricSetup = booleanPreferencesKey(name = "is_bio_setup")
        val listStyle = stringPreferencesKey(name = "list_style")
        val sorting = stringPreferencesKey(name = "sorting")
        val appPin = stringPreferencesKey(name = "app_pin")
    }

    private val dataStore = context.dataStore

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    suspend fun saveListStyleState(style: ListStyle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.listStyle] = style.parse()
        }
    }

    suspend fun saveListSortState(sorting: Sorting) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.sorting] = sorting.parse()
        }
    }

    suspend fun saveAppSettings(appSettings: AppSettings) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.isPinSetup] = appSettings.isAppLocked
            preferences[PreferencesKey.isBiometricSetup] = appSettings.isBiometricSetup
            preferences[PreferencesKey.appPin] = appSettings.appPin
            preferences[PreferencesKey.onBoardingKey] = appSettings.isOnBoardingCompleted
        }
    }

    fun readAppSettings() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            AppSettings(
                isAppLocked = preferences[PreferencesKey.isPinSetup] ?: false,
                isBiometricSetup = preferences[PreferencesKey.isBiometricSetup] ?: false,
                appPin = preferences[PreferencesKey.appPin] ?: "",
                isOnBoardingCompleted =  preferences[PreferencesKey.onBoardingKey] ?: false
            )
        }

    fun readListStyleState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            ListStyle.from(preferences[PreferencesKey.listStyle] ?: "")
        }

    fun readListSortState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            Sorting.from(preferences[PreferencesKey.sorting] ?: "")
        }

}

private fun Flow<Preferences>.catchExceptions(): Flow<Preferences> {
    return catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}