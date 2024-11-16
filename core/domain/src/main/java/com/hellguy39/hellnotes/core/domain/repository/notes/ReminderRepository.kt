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
package com.hellguy39.hellnotes.core.domain.repository.notes

import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun insertReminder(reminder: Reminder): Long

    suspend fun deleteReminder(reminder: Reminder)

    suspend fun deleteReminderById(id: Long)

    suspend fun deleteReminderByNoteId(noteId: Long)

    fun getAllRemindersStream(): Flow<List<Reminder>>

    suspend fun getAllReminders(): List<Reminder>

    suspend fun getReminderById(id: Long): Reminder?

    suspend fun getRemindersByNoteId(noteId: Long): List<Reminder>

    fun getRemindersByNoteIdStream(noteId: Long): Flow<List<Reminder>>

    suspend fun updateReminder(reminder: Reminder)
}
