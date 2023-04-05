package com.hellguy39.hellnotes.core.database.di

import com.hellguy39.hellnotes.core.database.HellNotesDatabase
import com.hellguy39.hellnotes.core.database.dao.*
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
    ): ReminderDao = database.reminderDao

    @Provides
    fun provideLabelDao(
        database: HellNotesDatabase,
    ): LabelDao = database.labelDao

    @Provides
    fun provideChecklistDao(
        database: HellNotesDatabase,
    ): ChecklistDao = database.checklistDao

    @Provides
    fun provideTrashDao(
        database: HellNotesDatabase,
    ): TrashDao = database.trashDao

}