package io.github.qobiljon.stress.core.api

import android.content.Context
import io.github.qobiljon.stress.R
import io.github.qobiljon.stress.core.api.requests.SignInRequest
import io.github.qobiljon.stress.utils.Storage
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException


object ApiHelper {
    private var apiInterface: ApiInterface? = null

    private fun getApiInterface(context: Context): ApiInterface {
        if (apiInterface == null) apiInterface = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(context.getString(R.string.api_base_url)).build().create(ApiInterface::class.java)
        return apiInterface!!
    }

    suspend fun signIn(context: Context, email: String, password: String): Boolean {
        return try {
            val result = getApiInterface(context).signIn(SignInRequest(email = email, password = password))
            val resultBody = result.body()
            if (result.errorBody() == null && result.isSuccessful && resultBody != null) {
                Storage.setAuthToken(context, authToken = resultBody.token)
                true
            } else false
        } catch (e: ConnectException) {
            false
        } catch (e: SocketTimeoutException) {
            false
        }
    }

    suspend fun submitOffBodyFile(context: Context, token: String, file: File): Boolean {
        return try {
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), file)
            val formData = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)
            val result = getApiInterface(context).submitOffBodyFile(
                token = "Token $token",
                file = formData,
                name = filename,
            )
            result.errorBody() == null && result.isSuccessful
        } catch (e: ConnectException) {
            false
        } catch (e: SocketTimeoutException) {
            false
        }
    }

    suspend fun submitAccFile(context: Context, token: String, file: File): Boolean {
        return try {
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), file)
            val formData = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)
            val result = getApiInterface(context).submitAccFile(
                token = "Token $token",
                file = formData,
                name = filename,
            )
            result.errorBody() == null && result.isSuccessful
        } catch (e: ConnectException) {
            false
        } catch (e: SocketTimeoutException) {
            false
        }
    }

    suspend fun submitPPGFile(context: Context, token: String, file: File): Boolean {
        return try {
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), file)
            val formData = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)
            val result = getApiInterface(context).submitPPGFile(
                token = "Token $token",
                file = formData,
                name = filename,
            )
            result.errorBody() == null && result.isSuccessful
        } catch (e: ConnectException) {
            false
        } catch (e: SocketTimeoutException) {
            false
        }
    }
}
