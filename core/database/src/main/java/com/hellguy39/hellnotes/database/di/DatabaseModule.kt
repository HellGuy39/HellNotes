package com.hellguy39.hellnotes.database.di

import android.content.Context
import androidx.room.Room
import com.hellguy39.hellnotes.database.HellNotesDatabase
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
        @ApplicationContext context: Context
    ): HellNotesDatabase = Room.databaseBuilder(
        context,
        HellNotesDatabase::class.java,
        "hellnotes-database"
    ).build()

}