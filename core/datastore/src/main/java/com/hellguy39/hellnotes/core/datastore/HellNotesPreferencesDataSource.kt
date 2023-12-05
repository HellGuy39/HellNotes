package com.hellguy39.hellnotes.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.model.repository.local.datastore.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hellnotes_pref")

class HellNotesPreferencesDataSource
@Inject
constructor(
    @ApplicationContext context: Context
) {

    private object PreferencesKey {
        val onBoarding = booleanPreferencesKey(name = "on_boarding_completed")
        val trashTip = booleanPreferencesKey(name = "trash_tip_checked")
        val lockType = stringPreferencesKey(name = "lock_type")
        val isUseBiometricData = booleanPreferencesKey(name = "is_use_biometric_data")
        val listStyle = stringPreferencesKey(name = "list_style")
        val noteStyle = stringPreferencesKey(name = "note_style")
        val sorting = stringPreferencesKey(name = "sorting")
        val password = stringPreferencesKey(name = "password")
        val noteSwipesEnabled = booleanPreferencesKey(name = "note_swipes_enabled")
        val noteSwipeRight = stringPreferencesKey(name = "note_swipe_left")
        val noteSwipeLeft = stringPreferencesKey(name = "note_swipe_right")
        val lastBackupDate = longPreferencesKey(name = "last_backup_date")
    }

    private object PreferencesDefaultValues {
        const val onBoarding = false
        const val trashTip = false
        val lockType = LockScreenType.None
        const val isUseBiometricData = false
        val listStyle = ListStyle.Column
        val noteStyle = NoteStyle.Outlined
        val sorting = Sorting.DateOfLastEdit
        const val password = ""
        const val noteSwipesEnabled = true
        val noteSwipeRight = NoteSwipe.Delete
        val noteSwipeLeft = NoteSwipe.Archive
        const val lastBackupDate = 0L
    }

    private val dataStore = context.dataStore

    suspend fun resetToDefault() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoarding] = PreferencesDefaultValues.onBoarding
            preferences[PreferencesKey.listStyle] = PreferencesDefaultValues.listStyle.string()
            preferences[PreferencesKey.sorting] = PreferencesDefaultValues.sorting.string()
            preferences[PreferencesKey.noteStyle] = PreferencesDefaultValues.noteStyle.tag
            preferences[PreferencesKey.lockType] = PreferencesDefaultValues.lockType.string()
            preferences[PreferencesKey.isUseBiometricData] = PreferencesDefaultValues.isUseBiometricData
            preferences[PreferencesKey.password] = PreferencesDefaultValues.password
            preferences[PreferencesKey.noteSwipesEnabled] = PreferencesDefaultValues.noteSwipesEnabled
            preferences[PreferencesKey.noteSwipeLeft] = PreferencesDefaultValues.noteSwipeLeft.string()
            preferences[PreferencesKey.noteSwipeRight] = PreferencesDefaultValues.noteSwipeRight.string()
            preferences[PreferencesKey.trashTip] = PreferencesDefaultValues.trashTip
            preferences[PreferencesKey.lastBackupDate] = PreferencesDefaultValues.lastBackupDate
        }
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoarding] = completed
        }
    }

    suspend fun saveListStyleState(style: ListStyle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.listStyle] = style.string()
        }
    }

    suspend fun saveListSortState(sorting: Sorting) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.sorting] = sorting.string()
        }
    }

    suspend fun saveNoteStyleState(noteStyle: NoteStyle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.noteStyle] = noteStyle.tag
        }
    }

    suspend fun saveSecurityState(state: SecurityState) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.lockType] = state.lockType.string()
            preferences[PreferencesKey.isUseBiometricData] = state.isUseBiometricData
            preferences[PreferencesKey.password] = state.password
        }
    }

    suspend fun saveNoteSwipesState(state: NoteSwipesState) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.noteSwipesEnabled] = state.enabled
            preferences[PreferencesKey.noteSwipeLeft] = state.swipeLeft.string()
            preferences[PreferencesKey.noteSwipeRight] = state.swipeRight.string()
        }
    }

    suspend fun saveTrashTipState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.trashTip] = completed
        }
    }

    suspend fun saveLastBackupDate(millis: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.lastBackupDate] = millis
        }
    }

    fun readOnBoardingState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            preferences[PreferencesKey.onBoarding] ?: PreferencesDefaultValues.onBoarding
        }

    fun readTrashTipState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            preferences[PreferencesKey.trashTip] ?: PreferencesDefaultValues.trashTip
        }

    fun readSecurityState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            SecurityState(
                lockType = LockScreenType.from(
                    s = preferences[PreferencesKey.lockType],
                    defaultValue = PreferencesDefaultValues.lockType
                ),
                password = preferences[PreferencesKey.password] ?: PreferencesDefaultValues.password,
                isUseBiometricData = preferences[PreferencesKey.isUseBiometricData] ?: PreferencesDefaultValues.isUseBiometricData,
            )
        }

    fun readListStyleState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            ListStyle.from(
                s = preferences[PreferencesKey.listStyle],
                defaultValue = PreferencesDefaultValues.listStyle
            )
        }

    fun readListSortState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            Sorting.from(
                s = preferences[PreferencesKey.sorting],
                defaultValue = PreferencesDefaultValues.sorting
            )
        }

    fun readNoteStyleState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            NoteStyle.fromTag(
                s = preferences[PreferencesKey.noteStyle],
                defaultValue = PreferencesDefaultValues.noteStyle
            )
        }

    fun readNoteSwipesState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            NoteSwipesState(
                enabled = preferences[PreferencesKey.noteSwipesEnabled] ?: PreferencesDefaultValues.noteSwipesEnabled,
                swipeLeft = NoteSwipe.from(
                    s = preferences[PreferencesKey.noteSwipeLeft],
                    defaultValue = PreferencesDefaultValues.noteSwipeLeft
                ),
                swipeRight = NoteSwipe.from(
                    s = preferences[PreferencesKey.noteSwipeRight],
                    defaultValue = PreferencesDefaultValues.noteSwipeRight
                )
            )
        }

    fun readLastBackupDate() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            preferences[PreferencesKey.lastBackupDate] ?: PreferencesDefaultValues.lastBackupDate
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