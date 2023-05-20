package io.github.qobiljon.stress.sensors.listeners

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import io.github.qobiljon.stress.utils.Storage

class AccListener : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        if (event.values.isEmpty()) return
        Storage.saveAccReading(
            timestamp = System.currentTimeMillis(),
            x = event.values[0],
            y = event.values[1],
            z = event.values[2],
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // not implemented
    }
}
