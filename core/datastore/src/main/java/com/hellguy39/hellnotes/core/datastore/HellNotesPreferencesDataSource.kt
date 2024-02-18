package com.hellguy39.hellnotes.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.model.repository.local.datastore.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hellnotes_pref")

class HellNotesPreferencesDataSource
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) {
        private object PreferencesKey {
            val onBoarding = booleanPreferencesKey(name = "on_boarding_completed")
            val trashTip = booleanPreferencesKey(name = "trash_tip_checked")
            val isUseBiometricData = booleanPreferencesKey(name = "is_use_biometric_data")
            val noteSwipesEnabled = booleanPreferencesKey(name = "note_swipes_enabled")

            val lockType = stringPreferencesKey(name = "lock_type")
            val listStyle = stringPreferencesKey(name = "list_style")
            val noteStyle = stringPreferencesKey(name = "note_style")
            val password = stringPreferencesKey(name = "password")
            val noteSwipeRight = stringPreferencesKey(name = "note_swipe_left")
            val noteSwipeLeft = stringPreferencesKey(name = "note_swipe_right")
            val lastBackupDate = longPreferencesKey(name = "last_backup_date")
        }

        private object PreferencesDefaultValues {
            const val ON_BOARDING = false
            const val TRASH_TIP = false
            const val IS_USE_BIOMETRIC_DATA = false
            const val PASSWORD = ""
            const val NOTE_SWIPES_ENABLED = true
            const val LAST_BACKUP_DATE = 0L
        }

        private val dataStore = context.dataStore

        suspend fun resetToDefault() {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.onBoarding] = PreferencesDefaultValues.ON_BOARDING
                preferences[PreferencesKey.isUseBiometricData] = PreferencesDefaultValues.IS_USE_BIOMETRIC_DATA
                preferences[PreferencesKey.password] = PreferencesDefaultValues.PASSWORD
                preferences[PreferencesKey.noteSwipesEnabled] = PreferencesDefaultValues.NOTE_SWIPES_ENABLED
                preferences[PreferencesKey.trashTip] = PreferencesDefaultValues.TRASH_TIP
                preferences[PreferencesKey.lastBackupDate] = PreferencesDefaultValues.LAST_BACKUP_DATE

                preferences[PreferencesKey.listStyle] = ListStyle.default().tag
                preferences[PreferencesKey.noteStyle] = NoteStyle.default().tag
                preferences[PreferencesKey.lockType] = LockScreenType.default().tag
                preferences[PreferencesKey.noteSwipeLeft] = NoteSwipe.defaultSwipeLeft().tag
                preferences[PreferencesKey.noteSwipeRight] = NoteSwipe.defaultSwipeRight().tag
            }
        }

        suspend fun saveOnBoardingState(completed: Boolean) {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.onBoarding] = completed
            }
        }

        suspend fun saveListStyleState(style: ListStyle) {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.listStyle] = style.tag
            }
        }

        suspend fun saveNoteStyleState(noteStyle: NoteStyle) {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.noteStyle] = noteStyle.tag
            }
        }

        suspend fun saveSecurityState(state: SecurityState) {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.lockType] = state.lockType.tag
                preferences[PreferencesKey.isUseBiometricData] = state.isUseBiometricData
                preferences[PreferencesKey.password] = state.password
            }
        }

        suspend fun saveNoteSwipesState(state: NoteSwipesState) {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.noteSwipesEnabled] = state.enabled
                preferences[PreferencesKey.noteSwipeLeft] = state.swipeLeft.tag
                preferences[PreferencesKey.noteSwipeRight] = state.swipeRight.tag
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

        fun readOnBoardingState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    preferences[PreferencesKey.onBoarding] ?: PreferencesDefaultValues.ON_BOARDING
                }

        fun readTrashTipState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    preferences[PreferencesKey.trashTip] ?: PreferencesDefaultValues.TRASH_TIP
                }

        fun readSecurityState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    SecurityState(
                        lockType = LockScreenType.fromTag(preferences[PreferencesKey.lockType]),
                        password =
                            preferences[PreferencesKey.password]
                                ?: PreferencesDefaultValues.PASSWORD,
                        isUseBiometricData =
                            preferences[PreferencesKey.isUseBiometricData]
                                ?: PreferencesDefaultValues.IS_USE_BIOMETRIC_DATA,
                    )
                }

        fun readListStyleState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    ListStyle.fromTag(
                        preferences[PreferencesKey.listStyle],
                    )
                }

        fun readNoteStyleState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    NoteStyle.fromTag(preferences[PreferencesKey.noteStyle])
                }

        fun readNoteSwipesState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    NoteSwipesState(
                        enabled =
                            preferences[PreferencesKey.noteSwipesEnabled]
                                ?: PreferencesDefaultValues.NOTE_SWIPES_ENABLED,
                        swipeLeft =
                            NoteSwipe.fromTag(
                                tag = preferences[PreferencesKey.noteSwipeLeft],
                                defaultValue = NoteSwipe.defaultSwipeLeft(),
                            ),
                        swipeRight =
                            NoteSwipe.fromTag(
                                tag = preferences[PreferencesKey.noteSwipeRight],
                                defaultValue = NoteSwipe.defaultSwipeRight(),
                            ),
                    )
                }

        fun readLastBackupDate() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    preferences[PreferencesKey.lastBackupDate]
                        ?: PreferencesDefaultValues.LAST_BACKUP_DATE
                }
    }
