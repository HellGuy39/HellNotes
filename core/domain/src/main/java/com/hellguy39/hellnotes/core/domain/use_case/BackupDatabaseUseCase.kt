package com.hellguy39.hellnotes.core.domain.use_case

import android.net.Uri
import com.hellguy39.hellnotes.core.domain.database.BackupManager
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.local.file.Backup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BackupDatabaseUseCase @Inject constructor(
    private val backupManager: BackupManager,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(filepath: Uri): Flow<Resource<Backup>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val backup = backupManager.createBackup(filepath)
                dataStoreRepository.saveLastBackupDate(System.currentTimeMillis())
                emit(Resource.Success(data = backup))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }

            emit(Resource.Loading(false))
        }
    }

}