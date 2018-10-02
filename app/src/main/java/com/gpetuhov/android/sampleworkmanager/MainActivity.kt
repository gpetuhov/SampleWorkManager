package com.gpetuhov.android.sampleworkmanager

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.gpetuhov.android.sampleworkmanager.work.MyWorker
import com.gpetuhov.android.sampleworkmanager.work.MyWorker1
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setOnClickListener {
            // We can add constraints for our work request
            val myConstraints = Constraints.Builder()
                    // Work request with this constraint will run
                    // when device is charging only.
                    .setRequiresCharging(true)
                    .build()

            // Create work request to run MyWorker for one time
            // and add our constraints to it.
            val myWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .setConstraints(myConstraints)
                    .build()

            // Enqueue work request into WorkManager
            WorkManager.getInstance().enqueue(myWorkRequest)

            observeWorkStatus(myWorkRequest, "Task complete")
        }

        // Chained tasks
        textView.setOnLongClickListener {
            val myWorkRequest1 = OneTimeWorkRequestBuilder<MyWorker1>().build()
            val myWorkRequest2 = OneTimeWorkRequestBuilder<MyWorker1>().build()
            val myWorkRequest3 = OneTimeWorkRequestBuilder<MyWorker1>().build()

            WorkManager.getInstance()
                    .beginWith(myWorkRequest1)
                    .then(myWorkRequest2)
                    .then(myWorkRequest3)
                    .enqueue()

            observeWorkStatus(myWorkRequest1, "Task 1 complete")
            observeWorkStatus(myWorkRequest2, "Task 2 complete")
            observeWorkStatus(myWorkRequest3, "Task 3 complete")

            true
        }
    }

    // Observe work status
    private fun observeWorkStatus(workRequest: WorkRequest, message: String) {
        WorkManager.getInstance().getStatusById(workRequest.id)
                .observe(this@MainActivity, Observer { workStatus ->
                    if (workStatus != null && workStatus.state.isFinished) {
                        toast(message)
                    }
                })
    }
}
