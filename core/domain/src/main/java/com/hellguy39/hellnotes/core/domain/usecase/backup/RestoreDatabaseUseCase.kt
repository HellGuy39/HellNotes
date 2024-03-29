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
