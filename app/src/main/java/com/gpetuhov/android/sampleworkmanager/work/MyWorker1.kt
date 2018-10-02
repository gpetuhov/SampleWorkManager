package com.gpetuhov.android.sampleworkmanager.work

import androidx.work.Worker

class MyWorker1 : Worker() {

    override fun doWork(): Result {
        Thread.sleep(1000)
        return Result.SUCCESS
    }
}

