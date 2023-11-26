package com.hellguy39.hellnotes.core.domain.use_case.backup

import android.net.Uri
import com.hellguy39.hellnotes.core.domain.manager.BackupManager
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.local.file.Backup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class BackupDatabaseUseCase
@Inject
constructor(
    private val backupManager: BackupManager,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(filepath: Uri): Flow<Resource<Backup>> {
        return flow<Resource<Backup>> {
            val backup = backupManager.createBackup(filepath)
            dataStoreRepository.saveLastBackupDate(System.currentTimeMillis())
            emit(Resource.Success(data = backup))
        }
            .catch { cause -> emit(Resource.Error(cause.message.toString())) }
            .onStart { emit(Resource.Loading(true)) }
            .onCompletion { emit(Resource.Loading(false)) }
            .flowOn(Dispatchers.IO)
    }
}