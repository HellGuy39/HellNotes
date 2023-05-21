package com.hellguy39.hellnotes.work

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.WorkDatabase
import androidx.work.impl.foreground.ForegroundProcessor
import androidx.work.impl.utils.WorkForegroundUpdater
import androidx.work.impl.utils.WorkProgressUpdater
import androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor
import com.hellguy39.hellnotes.core.data.repository.local.fake.FakeTrashRepository
import com.hellguy39.hellnotes.core.domain.use_case.DeleteExpiredNotesUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class DeleteExpiredNotesWorkerTest {

    private var context: Context? = null

    private var workerParameters: WorkerParameters? = null

    @Before
    fun onStart() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        val configuration = Configuration.Builder().build()
        val executor = configuration.executor
        val workManagerTaskExecutor = WorkManagerTaskExecutor(executor)
        val workDatabase = WorkDatabase.create(
            context!!,
            workManagerTaskExecutor.mainThreadExecutor,
            true
        )

        workerParameters = WorkerParameters(
            UUID.fromString("d1e5a17a-bed4-11ec-9d64-0242ac120002"),
            Data.EMPTY,
            listOf(),
            WorkerParameters.RuntimeExtras(),
            1,
            1,
            executor,
            workManagerTaskExecutor,
            configuration.workerFactory,
            WorkProgressUpdater(workDatabase, workManagerTaskExecutor),
            WorkForegroundUpdater(
                workDatabase,
                object : ForegroundProcessor {
                    override fun startForeground(
                        workSpecId: String,
                        foregroundInfo: ForegroundInfo
                    ) = Unit
                    override fun stopForeground(workSpecId: String) = Unit
                    override fun isEnqueuedInForeground(workSpecId: String): Boolean = true
                },
                workManagerTaskExecutor
            )
        )

    }

    @After
    fun onStop() {
        context = null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDeleteExpiredNotesWorker() = runTest {

        val repository = FakeTrashRepository()

        val worker = DeleteExpiredNotesWorker(
            context!!,
            workerParameters!!,
            DeleteExpiredNotesUseCase(repository)
        )

        val result = worker.doWork()

        assertTrue(result is ListenableWorker.Result.Success)
        assertTrue(repository.getAllTrash().isEmpty())
    }


}