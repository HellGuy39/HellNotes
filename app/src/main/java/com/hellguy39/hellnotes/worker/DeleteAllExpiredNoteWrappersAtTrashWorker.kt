package com.hellguy39.hellnotes.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.hellguy39.hellnotes.core.domain.usecase.trash.DeleteAllExpiredNoteWrappersAtTrashUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class DeleteAllExpiredNoteWrappersAtTrashWorker
    @AssistedInject
    constructor(
        @Assisted context: Context,
        @Assisted workerParameters: WorkerParameters,
        private val deleteAllExpiredNoteWrappersAtTrashUseCase: DeleteAllExpiredNoteWrappersAtTrashUseCase,
    ) : CoroutineWorker(context, workerParameters) {
        override suspend fun doWork(): Result {
            deleteAllExpiredNoteWrappersAtTrashUseCase.invoke()
            return Result.success()
        }

        companion object {
            const val WORK_NAME = "DeleteAllExpiredNoteWrappersAtTrashWorker"

            private fun buildConstraints(): Constraints {
                return Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresDeviceIdle(false)
                    .setRequiresStorageNotLow(false)
                    .setRequiresCharging(false)
                    .setRequiresBatteryNotLow(true)
                    .build()
            }

            fun buildRequest(): PeriodicWorkRequest {
                return PeriodicWorkRequestBuilder<DeleteAllExpiredNoteWrappersAtTrashWorker>(
                    repeatInterval = 7,
                    repeatIntervalTimeUnit = TimeUnit.DAYS,
                    flexTimeInterval = 12,
                    flexTimeIntervalUnit = TimeUnit.HOURS,
                ).setConstraints(buildConstraints())
                    .build()
            }
        }
    }
