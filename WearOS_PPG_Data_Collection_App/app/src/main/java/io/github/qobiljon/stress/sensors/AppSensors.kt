package io.github.qobiljon.stress.sensors

import android.hardware.Sensor
import android.hardware.SensorManager

enum class AppSensors(val type: String) {
    SENSOR_PPG("com.samsung.sensor.hr_raw") {
        override fun sensingRate() = SensorManager.SENSOR_DELAY_GAME
    },
    SENSOR_ACC(Sensor.STRING_TYPE_ACCELEROMETER) {
        override fun sensingRate() = SensorManager.SENSOR_DELAY_UI
    },
    SENSOR_OFF_BODY("com.samsung.sensor.low_power_offbody_detector");

    open fun sensingRate() = SensorManager.SENSOR_DELAY_NORMAL
}
