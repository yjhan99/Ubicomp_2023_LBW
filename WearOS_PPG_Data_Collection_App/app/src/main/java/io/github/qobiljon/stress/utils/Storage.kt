package io.github.qobiljon.stress.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import io.github.qobiljon.stress.core.api.ApiHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

object Storage {
    private const val KEY_PREFS_NAME = "shared_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private lateinit var accFile: File
    private lateinit var ppgFile: File
    private lateinit var offBodyFile: File

    private var accUploading = false
    private var ppgUploading = false
    private var offBodyUploading = false


    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun init(context: Context) {
        accFile = File(context.filesDir, "acc.csv")
        if (!accFile.exists()) accFile.createNewFile()

        ppgFile = File(context.filesDir, "ppg.csv")
        if (!ppgFile.exists()) ppgFile.createNewFile()

        offBodyFile = File(context.filesDir, "offBody.csv")
        if (!offBodyFile.exists()) offBodyFile.createNewFile()
    }

    fun syncToCloud(context: Context) {
        if (!isAuthenticated(context)) return

        runBlocking {
            if (!accUploading) {
                accUploading = true
                launch {
                    // prepare current acc file for upload
                    val newFile = File(context.filesDir, "${System.currentTimeMillis()}_acc.csv")
                    if (newFile.exists() && newFile.length() == 0L) newFile.delete()
                    if (accFile.length() > 0) {
                        accFile.copyTo(newFile)
                        accFile.delete()
                        accFile.createNewFile()
                    }

                    // upload all dangling files
                    val files = context.filesDir.listFiles { directory, filename -> directory.length() > 0 && filename.matches("""\d+_acc\.csv""".toRegex()) } ?: arrayOf<File>()
                    for (file in files) {
                        val success = ApiHelper.submitAccFile(
                            context = context,
                            token = getAuthToken(context),
                            file = file,
                        )
                        if (success) file.delete()
                    }
                    accUploading = false
                }
            }

            if (!ppgUploading) {
                ppgUploading = true
                launch {
                    // prepare current ppg file for upload
                    val newFile = File(context.filesDir, "${System.currentTimeMillis()}_ppg.csv")
                    if (newFile.exists() && newFile.length() == 0L) newFile.delete()
                    if (ppgFile.length() > 0) {
                        ppgFile.copyTo(newFile)
                        ppgFile.delete()
                        ppgFile.createNewFile()
                    }

                    // upload all dangling files
                    val files = context.filesDir.listFiles { directory, filename -> directory.length() > 0 && filename.matches("""\d+_ppg\.csv""".toRegex()) } ?: arrayOf<File>()
                    for (file in files) {
                        val success = ApiHelper.submitPPGFile(
                            context = context,
                            token = getAuthToken(context),
                            file = file,
                        )
                        if (success) file.delete()
                    }
                    ppgUploading = false
                }
            }

            if (!offBodyUploading) {
                offBodyUploading = true
                launch {
                    // prepare current offBody file for upload
                    val newFile = File(context.filesDir, "${System.currentTimeMillis()}_offBody.csv")
                    if (newFile.exists() && newFile.length() == 0L) newFile.delete()
                    if (offBodyFile.length() > 0) {
                        offBodyFile.copyTo(newFile)
                        offBodyFile.delete()
                        offBodyFile.createNewFile()
                    }

                    // upload all dangling files
                    val files = context.filesDir.listFiles { directory, filename -> directory.length() > 0 && filename.matches("""\d+_offBody\.csv""".toRegex()) } ?: arrayOf<File>()
                    for (file in files) {
                        val success = ApiHelper.submitOffBodyFile(
                            context = context,
                            token = getAuthToken(context),
                            file = file,
                        )
                        if (success) file.delete()
                    }
                    offBodyUploading = false
                }
            }
        }
    }

    fun isAuthenticated(context: Context): Boolean {
        // return getSharedPreferences(context).getString(KEY_AUTH_TOKEN, null) != null
        return true;
    }

    private fun getAuthToken(context: Context): String {
        return getSharedPreferences(context).getString(KEY_AUTH_TOKEN, null)!!
    }

    fun setAuthToken(context: Context, authToken: String) {
        getSharedPreferences(context).edit {
            putString(KEY_AUTH_TOKEN, authToken)
        }
    }

    fun saveOffBodyReading(timestamp: Long, isOffBody: Boolean) {
        offBodyFile.appendText("$timestamp,$isOffBody\n")
    }

    fun savePPGReading(timestamp: Long, a: Float?, b: Float?, c: Float?, d: Float?, e: Float?, f: Float?, g: Float?, h: Float?, i: Float?, j: Float?, k: Float?, l: Float?, m: Float?, n: Float?, o: Float?, p: Float?) {
        ppgFile.appendText("$timestamp,$a,$b,$c,$d,$e,$f,$g,$h,$i,$j,$k,$l,$m,$n,$o,$p\n")
    }

    fun saveAccReading(timestamp: Long, x: Float?, y: Float?, z: Float?) {
        accFile.appendText("$timestamp,$x,$y,$z\n")
    }
}
