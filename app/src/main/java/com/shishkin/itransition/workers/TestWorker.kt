package com.shishkin.itransition.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import javax.inject.Inject

class TestWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val testLogger: TestLogger
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        testLogger.log()
        return Result.success()
    }
}