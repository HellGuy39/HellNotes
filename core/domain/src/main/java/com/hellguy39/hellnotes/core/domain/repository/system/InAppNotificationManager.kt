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
package com.hellguy39.hellnotes.core.domain.tools

import android.app.PendingIntent
import com.hellguy39.hellnotes.core.common.id.randomId
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel

interface InAppNotificationManager {
    fun init()

    fun postNotification(
        notificationId: Int,
        title: String,
        body: String,
        pendingIntent: PendingIntent,
        channel: HellNotesNotificationChannel,
    )

    fun cancelNotification(notificationId: Int)
}

fun InAppNotificationManager.postReminderNotification(
    notificationId: Int = randomId(),
    title: String = "",
    body: String = "",
    pendingIntent: PendingIntent,
) {
    postNotification(
        notificationId = notificationId,
        title = title,
        body = body,
        pendingIntent = pendingIntent,
        channel = HellNotesNotificationChannel.Reminders,
    )
}
