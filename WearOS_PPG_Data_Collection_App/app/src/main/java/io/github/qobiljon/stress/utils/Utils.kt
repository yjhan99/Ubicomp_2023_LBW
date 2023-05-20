package io.github.qobiljon.stress.utils

import android.content.Context
import android.widget.Toast

object Utils {
    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
