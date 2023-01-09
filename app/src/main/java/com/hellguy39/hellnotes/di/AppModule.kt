package com.hellguy39.hellnotes.di

import android.app.Application
import com.hellguy39.hellnotes.domain.AlarmScheduler
import com.hellguy39.hellnotes.util.AndroidAlarmScheduler
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
    fun provideAlarmEvents(
        app: Application
    ): AlarmScheduler {
        return AndroidAlarmScheduler(app)
    }
}