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
package com.hellguy39.hellnotes.core.domain.usecase.reminder

import com.hellguy39.hellnotes.core.common.date.DateManager
import com.hellguy39.hellnotes.core.domain.repository.notes.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.system.AlarmScheduler
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import javax.inject.Inject

class ActivateReminderUseCase
    @Inject
    constructor(
        private val reminderRepository: ReminderRepository,
        private val alarmScheduler: AlarmScheduler,
    ) {
        suspend operator fun invoke(reminderId: Long) {
            // TODO: handle null
            val reminder = reminderRepository.getReminderById(reminderId) ?: return

            if (reminder.repeat is Repeat.DoesNotRepeat) {
                reminderRepository.deleteReminder(reminder)
            } else {
                val nextTriggerDate =
                    DateManager.from(reminder.triggerDate)
                        .increase(reminder.repeat)
                        .toMillis()
                val updatedReminder =
                    reminder.copy(triggerDate = nextTriggerDate)
                reminderRepository.updateReminder(updatedReminder)
                alarmScheduler.scheduleAlarm(updatedReminder)
            }
        }
    }
