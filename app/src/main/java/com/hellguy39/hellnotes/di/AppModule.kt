package com.hellguy39.hellnotes.di

import android.app.Application
import com.hellguy39.hellnotes.domain.AlarmEvents
import com.hellguy39.hellnotes.util.AlarmEventsImpl
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
    ): AlarmEvents {
        return AlarmEventsImpl(app)
    }
}