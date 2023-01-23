package com.hellguy39.hellnotes.core.database.di

import com.hellguy39.hellnotes.core.database.HellNotesDatabase
import com.hellguy39.hellnotes.core.database.dao.LabelDao
import com.hellguy39.hellnotes.core.database.dao.NoteDao
import com.hellguy39.hellnotes.core.database.dao.RemindDao
import com.hellguy39.hellnotes.core.database.dao.TrashDao
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

    @Provides
    fun provideTrashDao(
        database: HellNotesDatabase,
    ): TrashDao = database.trashDao

}