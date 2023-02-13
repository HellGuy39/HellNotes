package com.hellguy39.hellnotes.core.datastore.di

import android.content.Context
import com.hellguy39.hellnotes.core.datastore.HellNotesPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideHellNotesPreferencesDataSource(
        @ApplicationContext context: Context
    ) = HellNotesPreferencesDataSource(context)

}