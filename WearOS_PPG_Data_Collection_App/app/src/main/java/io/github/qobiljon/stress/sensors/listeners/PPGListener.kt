package io.github.qobiljon.stress.sensors.listeners

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import io.github.qobiljon.stress.utils.Storage

class PPGListener : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        if (event.values.isEmpty()) return

        Storage.savePPGReading(
            timestamp = System.currentTimeMillis(),
            a = event.values[0],
            b = event.values[1],
            c = event.values[2],
            d = event.values[3],
            e = event.values[4],
            f = event.values[5],
            g = event.values[6],
            h = event.values[7],
            i = event.values[8],
            j = event.values[9],
            k = event.values[10],
            l = event.values[11],
            m = event.values[12],
            n = event.values[13],
            o = event.values[14],
            p = event.values[15],
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // not implemented
    }
}
