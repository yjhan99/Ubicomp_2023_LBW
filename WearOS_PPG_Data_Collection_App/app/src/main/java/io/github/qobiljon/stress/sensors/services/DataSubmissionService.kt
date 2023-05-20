package io.github.qobiljon.stress.sensors.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.IBinder
import android.util.Log
import io.github.qobiljon.stress.R
import io.github.qobiljon.stress.ui.MainActivity
import io.github.qobiljon.stress.utils.Storage
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class DataSubmissionService : Service() {
    companion object {
        private const val DATA_SUBMISSION_INTERVAL = 60L
    }

    var isRunning = false
    private val mBinder: IBinder = LocalBinder()
    private val executor = Executors.newScheduledThreadPool(10)

    inner class LocalBinder : Binder() {
        @Suppress("unused")
        val getService: DataSubmissionService
            get() = this@DataSubmissionService
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        Log.e(MainActivity.TAG, "DataSubmissionService.onCreate()")

        // foreground svc
        val notificationId = 98766
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notificationChannelId = javaClass.name
        val notificationChannelName = "Data submission"
        val notificationChannel = NotificationChannel(notificationChannelId, notificationChannelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(this, notificationChannelId).setContentTitle(getString(R.string.app_name)).setContentText("Uploading data to server").setSmallIcon(R.mipmap.ic_stress_app).setContentIntent(pendingIntent).build()
        startForeground(notificationId, notification)

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(MainActivity.TAG, "DataSubmissionService.onStartCommand()")
        if (isRunning) return START_STICKY
        isRunning = true

        executor.scheduleAtFixedRate({ Storage.syncToCloud(applicationContext) }, 0L, DATA_SUBMISSION_INTERVAL, TimeUnit.SECONDS)
        return START_STICKY
    }
}
