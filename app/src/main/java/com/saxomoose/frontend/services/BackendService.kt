package com.saxomoose.frontend.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://demo.backend.test/api/"
private const val token = "1|cYpZHYCdcL5HDY0LsVd1PriWMTZwSkhjeeoffEhY"

val client = OkHttpClient.Builder()
    .addInterceptor(BackendInterceptor(token))
    .build()

class BackendInterceptor(token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest  = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()

        return chain.proceed(newRequest);
    }
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface BackendService {
    @GET("categories")
    suspend fun getCategories(): String
}

object BackendApi {
    val retrofitService : BackendService by lazy {
        retrofit.create(BackendService::class.java)
    }
}