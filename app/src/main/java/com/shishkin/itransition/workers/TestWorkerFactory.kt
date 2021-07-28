package com.shishkin.itransition.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

class TestWorkerFactory @Inject constructor(private val testLogger: TestLogger) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            TestWorker::class.java.name ->
                TestWorker(appContext, workerParameters, testLogger)
            else ->
                null
        }
    }
}