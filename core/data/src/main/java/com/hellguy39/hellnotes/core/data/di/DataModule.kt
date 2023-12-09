package com.hellguy39.hellnotes.core.data.di

import com.hellguy39.hellnotes.core.data.database.BackupManagerImpl
import com.hellguy39.hellnotes.core.data.repository.*
import com.hellguy39.hellnotes.core.data.repository.local.*
import com.hellguy39.hellnotes.core.data.repository.remote.GithubRepositoryServiceImpl
import com.hellguy39.hellnotes.core.domain.manager.BackupManager
import com.hellguy39.hellnotes.core.domain.repository.*
import com.hellguy39.hellnotes.core.domain.repository.local.*
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindNoteRepository(i: NoteRepositoryImpl): NoteRepository

    @Binds
    fun bindRemindRepository(i: ReminderRepositoryImpl): ReminderRepository

    @Binds
    fun bindLabelRepository(i: LabelRepositoryImpl): LabelRepository

    @Binds
    fun bindChecklistRepository(i: ChecklistRepositoryImpl): ChecklistRepository

    @Binds
    fun bindTrashRepository(i: TrashRepositoryImpl): TrashRepository

    @Binds
    fun bindDataStoreRepository(i: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    fun bindReleaseService(i: GithubRepositoryServiceImpl): GithubRepositoryService

    @Binds
    fun bindBackupManager(i: BackupManagerImpl): BackupManager
}
