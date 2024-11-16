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
package com.hellguy39.hellnotes.core.domain.usecase.backup

import android.net.Uri
import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.manager.BackupManager
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.local.file.Restore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RestoreDatabaseUseCase
    @Inject
    constructor(
        private val backupManager: BackupManager,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(filepath: Uri): Flow<Resource<Restore>> {
            return flow<Resource<Restore>> {
                val restore = backupManager.restoreFromBackup(filepath)
                emit(Resource.Success(data = restore))
            }
                .catch { cause -> emit(Resource.Error(cause.message.toString())) }
                .onStart { emit(Resource.Loading(true)) }
                .onCompletion { emit(Resource.Loading(false)) }
                .flowOn(ioDispatcher)
        }
    }
