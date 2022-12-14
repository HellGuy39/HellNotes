package com.hellguy39.hellnotes.database.di

import com.hellguy39.hellnotes.database.HellNotesDatabase
import com.hellguy39.hellnotes.database.dao.LabelDao
import com.hellguy39.hellnotes.database.dao.NoteDao
import com.hellguy39.hellnotes.database.dao.RemindDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesNoteDao(
        database: HellNotesDatabase,
    ): NoteDao = database.noteDao

    @Provides
    fun providesRemindDao(
        database: HellNotesDatabase,
    ): RemindDao = database.remindDao

    @Provides
    fun provideLabelDao(
        database: HellNotesDatabase,
    ): LabelDao = database.labelDao

}