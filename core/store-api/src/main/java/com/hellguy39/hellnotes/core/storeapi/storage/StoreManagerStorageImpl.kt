/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.storeapi.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store-api-storage")

@Singleton
class StoreManagerStorageImpl
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) : StoreManagerStorage {
        private val dataStore = context.dataStore

        private object PreferencesKey {
            val reviewCompleted = booleanPreferencesKey(name = "review_completed")
        }

        private object PreferencesDefaultValues {
            const val REVIEW_COMPLETED = false
        }

        override suspend fun saveReviewCompletedState(completed: Boolean) {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.reviewCompleted] = completed
            }
        }

        override fun readReviewCompletedState() =
            dataStore.data
                .catchExceptions()
                .map { preferences ->
                    preferences[PreferencesKey.reviewCompleted] ?: PreferencesDefaultValues.REVIEW_COMPLETED
                }
    }
