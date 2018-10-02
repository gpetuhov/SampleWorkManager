package com.gpetuhov.android.sampleworkmanager.work

import androidx.work.Worker

// This is the task to be run in background
class MyWorker : Worker() {

    override fun doWork(): Result {
        Thread.sleep(3000)
        return Result.SUCCESS
    }
}

