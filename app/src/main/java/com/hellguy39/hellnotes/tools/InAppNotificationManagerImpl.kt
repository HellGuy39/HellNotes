package com.hellguy39.hellnotes.tools

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import com.hellguy39.hellnotes.component.broadcast.NotificationActionReceiver
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.id.randomId
import com.hellguy39.hellnotes.core.common.logger.taggedLogger
import com.hellguy39.hellnotes.core.common.permission.canPostNotifications
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel
import com.hellguy39.hellnotes.core.ui.decorator.NotificationChannelDecorator
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
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

        override fun init() {
            logger.i { "Initializing notification channels" }
            val notificationChannels = getNotificationChannels()
            notificationChannels.forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }

        override fun postNotification(
            notificationId: Int,
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
                    notificationId = notificationId,
                )

            if (context.canPostNotifications()) {
                notificationManager.notify(notificationId, notification)
                logger.i { "Notification $notificationId posted to ${channel.info.name} channel" }
            } else {
                logger.i { "Unable to send notifications: Access denied" }
            }
        }

        override fun cancelNotification(notificationId: Int) {
            logger.i { "Cancelling notification $notificationId" }
            notificationManager.cancel(notificationId)
        }

        private fun buildNotification(
            title: String,
            body: String,
            pendingIntent: PendingIntent,
            channel: HellNotesNotificationChannel,
            notificationId: Int,
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
                .setActions(channel, notificationId)
                .build()
        }

        private fun Notification.Builder.setActions(
            channel: HellNotesNotificationChannel,
            notificationId: Int,
        ): Notification.Builder {
            return apply {
                val actions = getNotificationActions(channel, notificationId)
                actions.forEach { action ->
                    addAction(action)
                }
            }
        }

        private fun getNotificationActions(
            channel: HellNotesNotificationChannel,
            notificationId: Int,
        ): List<Notification.Action> {
            return when (channel) {
                HellNotesNotificationChannel.Reminders -> {
                    listOf(
                        Notification.Action
                            .Builder(
                                Icon.createWithResource(context, AppIcons.Info),
                                context.getString(AppStrings.Button.Done),
                                PendingIntent.getBroadcast(
                                    context,
                                    randomId(),
                                    Intent(
                                        context,
                                        NotificationActionReceiver::class.java,
                                    )
                                        .apply {
                                            action = context.getString(AppStrings.Action.NotificationDone)
                                            putExtra(Arguments.NotificationId.key, notificationId)
                                        },
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
