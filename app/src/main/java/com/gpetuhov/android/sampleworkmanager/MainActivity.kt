package com.gpetuhov.android.sampleworkmanager

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.gpetuhov.android.sampleworkmanager.work.MyWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setOnClickListener {
            // Create work request to run MyWorker for one time
            val compressionWork = OneTimeWorkRequestBuilder<MyWorker>().build()

            // Enqueue work request into WorkManager
            WorkManager.getInstance().enqueue(compressionWork)

//            WorkManager.getInstance().getStatusById(compressionWork.id)
//                    .observe(this@MainActivity, Observer<MyWorker> { workStatus ->
//                        // Do something with the status
//                        if (workStatus != null && workStatus.state.isFinished) {
//                            // ...
//                        }
//                    })
        }
    }
}
