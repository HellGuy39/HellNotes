package com.hellguy39.hellnotes.tools

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import com.hellguy39.hellnotes.core.common.id.randomId
import com.hellguy39.hellnotes.core.common.logger.taggedLogger
import com.hellguy39.hellnotes.core.common.permission.canPostNotifications
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel
import com.hellguy39.hellnotes.core.ui.decorator.NotificationChannelDecorator
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InAppNotificationManagerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : InAppNotificationManager {
        private val notificationManager by lazy { context.getSystemService(NotificationManager::class.java) }
        private val channelDecorator by lazy { NotificationChannelDecorator.from(context) }

        private val logger by taggedLogger("InAppNotificationManagerImpl")

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
                logger.i { "Notification posted to ${channel.info.name} channel" }
            } else {
                logger.i { "Unable to send notifications: Access denied" }
            }
        }

        override fun init() {
            logger.i { "Initializing notification channels" }
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
                .setAutoCancel(true)
                .setShowWhen(true)
                .setWhen(System.currentTimeMillis())
                .setActions(channel)
                .build()
        }

        private fun Notification.Builder.setActions(
            channel: HellNotesNotificationChannel,
        ): Notification.Builder {
            return apply {
                val actions = getNotificationActions(channel)
                actions.forEach { action ->
                    addAction(action)
                }
            }
        }

        // TODO: Finish notification actions
        private fun getNotificationActions(
            channel: HellNotesNotificationChannel,
        ): List<Notification.Action> {
            return when (channel) {
                HellNotesNotificationChannel.Reminders -> {
                    listOf(
                        Notification.Action
                            .Builder(
                                Icon.createWithResource(context, AppIcons.Info),
                                "Set aside for 15 min",
                                PendingIntent.getBroadcast(
                                    context,
                                    1,
                                    Intent(),
                                    PendingIntent.FLAG_IMMUTABLE,
                                ),
                            )
                            .build(),
                        Notification.Action
                            .Builder(
                                Icon.createWithResource(context, AppIcons.Info),
                                "Done",
                                PendingIntent.getBroadcast(
                                    context,
                                    1,
                                    Intent(),
                                    PendingIntent.FLAG_IMMUTABLE,
                                ),
                            )
                            .build(),
                    )
                }
            }
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
