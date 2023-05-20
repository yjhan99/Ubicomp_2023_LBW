package io.github.qobiljon.stress.sensors.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import android.util.Log
import io.github.qobiljon.stress.R
import io.github.qobiljon.stress.sensors.AppSensors
import io.github.qobiljon.stress.sensors.listeners.*
import io.github.qobiljon.stress.ui.MainActivity
import java.util.*


class DataCollectionService : Service() {
    private val mBinder: IBinder = LocalBinder()
    private val listeners = mutableListOf<SensorEventListener>()
    var isRunning = false

    inner class LocalBinder : Binder() {
        @Suppress("unused")
        val getService: DataCollectionService
            get() = this@DataCollectionService
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        Log.e(MainActivity.TAG, "MotionHRService.onCreate()")

        // foreground svc
        val notificationId = 98764
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notificationChannelId = javaClass.name
        val notificationChannelName = "Motion and HR data collection"
        val notificationChannel = NotificationChannel(notificationChannelId, notificationChannelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(this, notificationChannelId).setContentTitle(getString(R.string.app_name)).setContentText("Collecting motion and HR data...").setSmallIcon(R.mipmap.ic_stress_app).setContentIntent(pendingIntent).build()
        startForeground(notificationId, notification)

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(MainActivity.TAG, "MotionHRService.onStartCommand()")
        if (isRunning) return START_STICKY
        else isRunning = true

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)

        mapOf(
            AppSensors.SENSOR_PPG to PPGListener(),
            AppSensors.SENSOR_ACC to AccListener(),
            AppSensors.SENSOR_OFF_BODY to OffBodyListener(applicationContext),
        ).forEach { (appSensor, listener) ->
            sensors.find { s -> s.stringType.equals(appSensor.type) }?.let { sensor ->
                sensorManager.registerListener(listener, sensor, appSensor.sensingRate())
                listeners.add(listener)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.e(MainActivity.TAG, "MotionHRService.onDestroy()")

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listeners.forEach { sensorManager.unregisterListener(it) }
        listeners.clear()
        isRunning = false

        super.onDestroy()
    }
}
