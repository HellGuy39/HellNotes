package com.hellguy39.hellnotes.core.domain.use_case

import android.net.Uri
import com.hellguy39.hellnotes.core.domain.database.BackupManager
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.local.file.Restore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestoreDatabaseUseCase @Inject constructor(
    private val backupManager: BackupManager
) {

    suspend operator fun invoke(filepath: Uri): Flow<Resource<Restore>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val restore = backupManager.restoreFromBackup(filepath)
                emit(Resource.Success(data = restore))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }

            emit(Resource.Loading(false))
        }
    }

}