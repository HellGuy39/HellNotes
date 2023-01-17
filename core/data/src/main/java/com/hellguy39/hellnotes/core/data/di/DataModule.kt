package com.hellguy39.hellnotes.core.data.di

import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.data.repository.AppSettingsRepositoryImpl
import com.hellguy39.hellnotes.core.data.repository.LabelRepositoryImpl
import com.hellguy39.hellnotes.core.data.repository.NoteRepositoryImpl
import com.hellguy39.hellnotes.core.data.repository.ReminderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindNoteRepository(
        i: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    fun bindAppSettingsRepository(
        i: AppSettingsRepositoryImpl
    ): AppSettingsRepository

    @Binds
    fun bindRemindRepository(
        i: ReminderRepositoryImpl
    ): ReminderRepository

    @Binds
    fun bindLabelRepository(
        i: LabelRepositoryImpl
    ): LabelRepository

}