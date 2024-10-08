package com.rvc.workmanagersample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        createNotification("Background Task", "This notification is generated by workManager")
        return Result.success()
    }

    public fun createNotification(title:String , msg : String)
    {

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("101","channel",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext,"101")
            .setContentTitle(title)
            .setContentText(msg)
            .setSmallIcon(R.drawable.ic_launcher_background)

        notificationManager.notify(1,notificationBuilder.build())

    }


}