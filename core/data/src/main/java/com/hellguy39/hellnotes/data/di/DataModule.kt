package com.hellguy39.hellnotes.data.di

import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTopicRepository(
        notesRepository: NoteRepositoryImpl
    ): NoteRepository

}