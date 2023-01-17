package com.hellguy39.hellnotes.di

import android.app.Application
import com.hellguy39.hellnotes.android_features.AndroidAlarmScheduler
import com.hellguy39.hellnotes.android_features.AndroidBiometricAuthenticator
import com.hellguy39.hellnotes.android_features.AndroidNotificationSender
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.NotificationSender
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAlarmScheduler(
        app: Application
    ): AlarmScheduler {
        return AndroidAlarmScheduler(app)
    }

    @Singleton
    @Provides
    fun provideBiometricAuthenticator(
        app: Application
    ): BiometricAuthenticator {
        return AndroidBiometricAuthenticator(app)
    }

    @Singleton
    @Provides
    fun provideNotificationSender(
        app: Application
    ): NotificationSender {
        return AndroidNotificationSender(app)
    }
}