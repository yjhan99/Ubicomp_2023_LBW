package io.github.qobiljon.stress

import android.app.Application
import io.github.qobiljon.stress.utils.Storage


class WearOSApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Storage.init(applicationContext)
    }
}