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
package com.hellguy39.hellnotes.tools

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.hellguy39.hellnotes.component.broadcast.ReminderReceiver
import com.hellguy39.hellnotes.core.common.api.ApiCapabilities
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.logger.taggedLogger
import com.hellguy39.hellnotes.core.domain.repository.system.AlarmScheduler
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmSchedulerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : AlarmScheduler {
        private val alarmManager by lazy { context.getSystemService(AlarmManager::class.java) }

        private val logger by taggedLogger("AlarmSchedulerImpl")

        override fun scheduleAlarm(reminder: Reminder) {
            if (!canScheduleAlarm()) {
                logger.i { "Alarm ${reminder.id} can't be scheduled" }
                return
            }

            logger.i { "Alarm ${reminder.id} scheduled" }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminder.triggerDate,
                buildPendingIntent(reminder),
            )
        }

        override fun cancelAlarm(reminder: Reminder) {
            logger.i { "Alarm ${reminder.id} cancelled" }
            alarmManager.cancel(buildPendingIntent(reminder))
        }

        private fun canScheduleAlarm() =
            if (ApiCapabilities.scheduleExactAlarmsPermissionRequired) {
                alarmManager.canScheduleExactAlarms()
            } else {
                true
            }

        private fun buildPendingIntent(reminder: Reminder): PendingIntent {
            return PendingIntent.getBroadcast(
                context,
                reminder.id?.toInt() ?: 0,
                buildIntent(reminder),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }

        private fun buildIntent(reminder: Reminder): Intent {
            return Intent(context, ReminderReceiver::class.java).apply {
                putExtras(
                    bundleOf(
                        Arguments.ReminderId.key to reminder.id,
                        Arguments.Message.key to reminder.message,
                        Arguments.NoteId.key to reminder.noteId,
                    ),
                )
            }
        }
    }
