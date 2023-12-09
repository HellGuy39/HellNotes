package com.hellguy39.hellnotes.di

import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.domain.tools.AlarmScheduler
import com.hellguy39.hellnotes.core.domain.tools.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.tools.Downloader
import com.hellguy39.hellnotes.core.domain.tools.LanguageHolder
import com.hellguy39.hellnotes.core.domain.tools.NotificationSender
import com.hellguy39.hellnotes.tools.AlarmSchedulerImpl
import com.hellguy39.hellnotes.tools.AnalyticsLoggerImpl
import com.hellguy39.hellnotes.tools.BiometricAuthenticatorImpl
import com.hellguy39.hellnotes.tools.DownloaderImpl
import com.hellguy39.hellnotes.tools.LanguageHolderImpl
import com.hellguy39.hellnotes.tools.NotificationSenderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    fun bindAnalyticsLogger(i: AnalyticsLoggerImpl): AnalyticsLogger

    @Binds
    fun bindAlarmScheduler(i: AlarmSchedulerImpl): AlarmScheduler

    @Binds
    fun bindBiometricAuthenticator(i: BiometricAuthenticatorImpl): BiometricAuthenticator

    @Binds
    fun bindNotificationSender(i: NotificationSenderImpl): NotificationSender

    @Binds
    fun bindLanguageHolder(i: LanguageHolderImpl): LanguageHolder

    @Binds
    fun bindDownloader(i: DownloaderImpl): Downloader
}
