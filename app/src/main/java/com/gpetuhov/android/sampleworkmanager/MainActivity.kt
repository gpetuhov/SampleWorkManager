package com.gpetuhov.android.sampleworkmanager

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.gpetuhov.android.sampleworkmanager.work.MyWorker
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

            // Observe work status
            WorkManager.getInstance().getStatusById(myWorkRequest.id)
                    .observe(this@MainActivity, Observer { workStatus ->
                        if (workStatus != null && workStatus.state.isFinished) {
                            toast("Task complete")
                        }
                    })
        }
    }
}
