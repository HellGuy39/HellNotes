package com.hellguy39.hellnotes.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DeleteExpiredNotesFromTrashWorker(
    context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return Result.success()
    }

}