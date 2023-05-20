package io.github.qobiljon.stress.core.api

import io.github.qobiljon.stress.core.api.requests.*
import io.github.qobiljon.stress.core.api.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {
    @POST("sign_in")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("submit_off_body")
    @Multipart
    suspend fun submitOffBodyFile(@Header("Authorization") token: String, @Part file: MultipartBody.Part, @Part("value") name: RequestBody): Response<Void>

    @POST("submit_ppg")
    @Multipart
    suspend fun submitPPGFile(@Header("Authorization") token: String, @Part file: MultipartBody.Part, @Part("value") name: RequestBody): Response<Void>

    @POST("submit_acc")
    @Multipart
    suspend fun submitAccFile(@Header("Authorization") token: String, @Part file: MultipartBody.Part, @Part("value") name: RequestBody): Response<Void>
}
