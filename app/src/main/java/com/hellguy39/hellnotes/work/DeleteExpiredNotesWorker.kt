package com.hellguy39.hellnotes.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.hellguy39.hellnotes.core.domain.use_case.trash.DeleteExpiredNotesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class DeleteExpiredNotesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val deleteExpiredNotesUseCase: DeleteExpiredNotesUseCase
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        deleteExpiredNotesUseCase.invoke()
        return Result.success()
    }

    companion object {

        const val TAG = "DeleteExpiredNotesFromTrashWorker"

        fun getWorkRequest(): PeriodicWorkRequest {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresDeviceIdle(false)
                .setRequiresStorageNotLow(false)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(true)
                .build()

            return PeriodicWorkRequestBuilder<DeleteExpiredNotesWorker>(
                7, TimeUnit.DAYS,
                8, TimeUnit.DAYS
                )
                .setConstraints(constraints)
                .build()
        }

    }

}