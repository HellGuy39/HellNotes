package com.hellguy39.hellnotes.database.di

import com.hellguy39.hellnotes.database.HellNotesDatabase
import com.hellguy39.hellnotes.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesAuthorDao(
        database: HellNotesDatabase,
    ): NoteDao = database.noteDao

}