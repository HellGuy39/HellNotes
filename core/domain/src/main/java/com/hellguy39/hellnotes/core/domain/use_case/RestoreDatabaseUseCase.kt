package com.hellguy39.hellnotes.core.domain.use_case

import android.net.Uri
import com.hellguy39.hellnotes.core.domain.database.BackupManager
import com.hellguy39.hellnotes.core.model.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestoreDatabaseUseCase @Inject constructor(
    private val backupManager: BackupManager
) {

    suspend operator fun invoke(filepath: Uri): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            backupManager.restore(filepath)
            emit(Resource.Loading(false))
        }
    }

}