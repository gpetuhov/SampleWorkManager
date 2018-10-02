package com.gpetuhov.android.sampleworkmanager.work

import android.util.Log
import androidx.work.Worker

// This is the task to be run in background
class MyWorker : Worker() {

    override fun doWork(): Result {
        Thread.sleep(3000)
        Log.d("MyWorker", "Task complete")
        return Result.SUCCESS
    }
}

