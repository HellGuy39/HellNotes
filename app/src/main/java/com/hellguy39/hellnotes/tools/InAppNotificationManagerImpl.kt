package com.hellguy39.hellnotes.tools

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.drawable.Icon
import com.hellguy39.hellnotes.core.common.id.randomId
import com.hellguy39.hellnotes.core.common.permission.canPostNotifications
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel
import com.hellguy39.hellnotes.core.ui.decorator.NotificationChannelDecorator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InAppNotificationManagerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : InAppNotificationManager {
        private val notificationManager by lazy { context.getSystemService(NotificationManager::class.java) }
        private val channelDecorator by lazy { NotificationChannelDecorator.from(context) }

        override fun postNotification(
            title: String,
            body: String,
            pendingIntent: PendingIntent,
            channel: HellNotesNotificationChannel,
        ) {
            val notification =
                buildNotification(
                    title = title.ifBlank { channelDecorator.getTitle(channel) },
                    body = body.ifBlank { channelDecorator.getBody(channel) },
                    pendingIntent = pendingIntent,
                    channel = channel,
                )

            if (context.canPostNotifications()) {
                notificationManager.notify(randomId(), notification)
            }
        }

        override fun init() {
            val notificationChannels = getNotificationChannels()
            notificationChannels.forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }

        private fun buildNotification(
            title: String,
            body: String,
            pendingIntent: PendingIntent,
            channel: HellNotesNotificationChannel,
        ): Notification {
            return Notification
                .Builder(context, channel.info.id)
                .setSmallIcon(channelDecorator.getSmallIcon(channel))
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .build()
        }

        private fun getNotificationChannels(): List<NotificationChannel> {
            return HellNotesNotificationChannel.asList()
                .map { channel ->
                    val channelInfo = channel.info
                    NotificationChannel(
                        channelInfo.id,
                        channelInfo.name,
                        channelInfo.importance,
                    )
                        .apply {
                            description = channelDecorator.getDescription(channel)
                        }
                }
        }
    }
