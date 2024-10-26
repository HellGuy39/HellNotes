/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    fun bindDataStoreRepository(i: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    fun bindReleaseService(i: GithubRepositoryServiceImpl): GithubRepositoryService

    @Binds
    fun bindBackupManager(i: BackupManagerImpl): BackupManager

    @Binds
    fun bindNoteActionController(i: NoteActionControllerImpl): NoteActionController
}
