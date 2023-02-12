package com.hellguy39.hellnotes.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.LockScreenType
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
        val onTrashTipKey = booleanPreferencesKey(name = "on_trash_tip_checked")
        val appLockType = stringPreferencesKey(name = "app_lock_type")
        val isUseBiometricData = booleanPreferencesKey(name = "is_use_biometric_data")
        val listStyle = stringPreferencesKey(name = "list_style")
        val sorting = stringPreferencesKey(name = "sorting")
        val appCode = stringPreferencesKey(name = "app_code")
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

    suspend fun saveAppCode(code: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.appCode] = code
        }
    }

    suspend fun saveIsUseBiometricData(isUseBiometricData: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.isUseBiometricData] = isUseBiometricData
        }
    }

    suspend fun saveAppLockType(lockScreenType: LockScreenType) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.appLockType] = lockScreenType.parse()
        }
    }

    suspend fun saveAppSettings(
        appSettings: AppSettings
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.appLockType] = appSettings.appLockType.parse()
            preferences[PreferencesKey.isUseBiometricData] = appSettings.isUseBiometricData
            preferences[PreferencesKey.appCode] = appSettings.appCode
            preferences[PreferencesKey.onBoardingKey] = appSettings.isOnBoardingCompleted
        }
    }

    suspend fun saveOnTrashTipChecked(
        onChecked: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onTrashTipKey] = onChecked
        }
    }

    fun readAppSettings() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            AppSettings(
                appLockType = LockScreenType.from(preferences[PreferencesKey.appLockType]),
                appCode = preferences[PreferencesKey.appCode] ?: "",
                isUseBiometricData = preferences[PreferencesKey.isUseBiometricData] ?: false,
                isOnBoardingCompleted = preferences[PreferencesKey.onBoardingKey] ?: false,
                isTrashTipChecked = preferences[PreferencesKey.onTrashTipKey] ?: false,
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