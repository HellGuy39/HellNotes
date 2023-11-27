package com.hellguy39.hellnotes.di

import com.hellguy39.hellnotes.android_features.*
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.domain.system_features.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindAnalyticsLogger(
        i: AnalyticsLoggerImpl
    ): AnalyticsLogger

    @Binds
    fun bindAlarmScheduler(
        i: AndroidAlarmScheduler
    ): AlarmScheduler

    @Binds
    fun bindBiometricAuthenticator(
        i: AndroidBiometricAuthenticator
    ): BiometricAuthenticator

    @Binds
    fun bindNotificationSender(
        i: AndroidNotificationSender
    ): NotificationSender

    @Binds
    fun bindLanguageHolder(
        i: AndroidLanguageHolder
    ): LanguageHolder

    @Binds
    fun bindDownloader(
        i: AndroidDownloader
    ): Downloader

}