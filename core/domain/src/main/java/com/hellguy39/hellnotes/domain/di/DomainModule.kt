package com.hellguy39.hellnotes.domain.di

import com.hellguy39.hellnotes.domain.note.IsNoteValidUseCase
import com.hellguy39.hellnotes.domain.note.ShareNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
//
//    @Provides
//    @Singleton
//    fun provideIsNoteValidUseCase(): IsNoteValidUseCase = IsNoteValidUseCase()
//
//    @Provides
//    @Singleton
//    fun provideShareNoteUseCase(): ShareNoteUseCase = ShareNoteUseCase()

}