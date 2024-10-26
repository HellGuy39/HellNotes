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
package com.hellguy39.hellnotes.component.broadcast

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.component.main.MainActivity
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.common.logger.taggedLogger
import com.hellguy39.hellnotes.core.common.uri.DeeplinkRoute
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import com.hellguy39.hellnotes.core.domain.tools.postReminderNotification
import com.hellguy39.hellnotes.core.domain.usecase.reminder.ActivateReminderUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject lateinit var reminderRepository: ReminderRepository

    @Inject lateinit var inAppNotificationManager: InAppNotificationManager

    @Inject lateinit var activateReminderUseCase: ActivateReminderUseCase

    private val logger by taggedLogger("ReminderReceiver")

    private val scope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        logger.i { "Reminder received" }
        if (context == null || intent == null) return

        val message = intent.getArgument(Arguments.Message)
        val noteId = intent.getArgument(Arguments.NoteId)
        val reminderId = intent.getArgument(Arguments.ReminderId)

        logger.i { "Arguments: noteId - $noteId, reminderId - $reminderId, message - $message" }

        if (Arguments.NoteId.isEmpty(noteId) && Arguments.ReminderId.isEmpty(reminderId)) return

        inAppNotificationManager.postReminderNotification(
            body = message,
            pendingIntent = buildPendingIntent(context, noteId, reminderId),
        )

        scope.launch {
            activateReminderUseCase.invoke(reminderId)
        }
    }

    private fun buildPendingIntent(context: Context, noteId: Long, reminderId: Long): PendingIntent {
        val openNoteIntent =
            Intent(
                Intent.ACTION_VIEW,
                DeeplinkRoute.fromApp().passArgument(Arguments.NoteId, noteId).asUri(),
                context,
                MainActivity::class.java,
            )

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(openNoteIntent)
            getPendingIntent(
                reminderId.toInt(),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }
    }
}
