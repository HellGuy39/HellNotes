package com.hellguy39.hellnotes.core.database.di

import android.content.Context
import com.hellguy39.hellnotes.core.database.HellNotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideHellNotesDatabase(
        @ApplicationContext context: Context,
    ): HellNotesDatabase = HellNotesDatabase.getDatabase(context)
}
