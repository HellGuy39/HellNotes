package com.hellguy39.hellnotes.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.NoteSwipesState
import com.hellguy39.hellnotes.core.model.util.*
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
        val trashTipKey = booleanPreferencesKey(name = "trash_tip_checked")
        val lockType = stringPreferencesKey(name = "lock_type")
        val isUseBiometricData = booleanPreferencesKey(name = "is_use_biometric_data")
        val listStyle = stringPreferencesKey(name = "list_style")
        val noteStyle = stringPreferencesKey(name = "note_style")
        val sorting = stringPreferencesKey(name = "sorting")
        val password = stringPreferencesKey(name = "password")

        val noteSwipesEnabled = booleanPreferencesKey(name = "note_swipes_enabled")
        val noteSwipeRight = stringPreferencesKey(name = "note_swipe_left")
        val noteSwipeLeft = stringPreferencesKey(name = "note_swipe_right")
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

    suspend fun saveNoteStyleState(noteStyle: NoteStyle) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.noteStyle] = noteStyle.parse()
        }
    }

    suspend fun saveSecurityState(state: SecurityState) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.lockType] = state.lockType.parse()
            preferences[PreferencesKey.isUseBiometricData] = state.isUseBiometricData
            preferences[PreferencesKey.password] = state.password
        }
    }

    suspend fun saveNoteSwipesState(state: NoteSwipesState) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.noteSwipesEnabled] = state.enabled
            preferences[PreferencesKey.noteSwipeLeft] = state.swipeLeft.parse()
            preferences[PreferencesKey.noteSwipeRight] = state.swipeRight.parse()
        }
    }

    suspend fun saveTrashTipState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.trashTipKey] = completed
        }
    }

    fun readOnBoardingState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            preferences[PreferencesKey.onBoardingKey] ?: false
        }

    fun readTrashTipState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            preferences[PreferencesKey.trashTipKey] ?: false
        }

    fun readSecurityState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            SecurityState(
                lockType = LockScreenType.from(preferences[PreferencesKey.lockType]),
                password = preferences[PreferencesKey.password] ?: "",
                isUseBiometricData = preferences[PreferencesKey.isUseBiometricData] ?: false,
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

    fun readNoteStyleState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            NoteStyle.from(preferences[PreferencesKey.noteStyle] ?: "")
        }

    fun readNoteSwipesState() = dataStore.data
        .catchExceptions()
        .map { preferences ->
            NoteSwipesState(
                enabled = preferences[PreferencesKey.noteSwipesEnabled] ?: true,
                swipeLeft = NoteSwipe.from(
                    preferences[PreferencesKey.noteSwipeLeft],
                    NoteSwipe.Archive
                ),
                swipeRight = NoteSwipe.from(
                    preferences[PreferencesKey.noteSwipeRight],
                    NoteSwipe.Delete
                )
            )
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