package com.hellguy39.hellnotes.data.di

import com.hellguy39.hellnotes.data.repository.AppSettingsRepository
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.data.repository.impl.AppSettingsRepositoryImpl
import com.hellguy39.hellnotes.data.repository.impl.NoteRepositoryImpl
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

}