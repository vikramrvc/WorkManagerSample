package com.rvc.workmanagersample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.rvc.workmanagersample.R.id.clickbtn
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        button = findViewById(clickbtn)

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).setConstraints(constraints).build()

        val periodicWork = PeriodicWorkRequest.Builder(UploadWorker::class.java,20,TimeUnit.MINUTES)

        var workList = ArrayList<WorkRequest>();
        workList.add(uploadRequest)
        workList.add(periodicWork)



        button.setOnClickListener {

            WorkManager.getInstance(applicationContext).enqueue(periodicWork)
        }

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(periodicWork.id)
            .observe(this, Observer {
                val status: String = it.state.name
                Toast.makeText(this,status, Toast.LENGTH_SHORT).show()
            })
    }
}