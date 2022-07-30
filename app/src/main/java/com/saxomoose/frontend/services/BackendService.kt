package com.saxomoose.frontend.services

import com.saxomoose.frontend.models.Category
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://demo.backend.test/api/"
private const val token = "1|cYpZHYCdcL5HDY0LsVd1PriWMTZwSkhjeeoffEhY"
private const val TOP_LEVEL_FIELD = "data"

val client = OkHttpClient.Builder()
    .addInterceptor(BackendInterceptor())
    .build()

// Adds a bearer token to the request headers.
class BackendInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest  = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()

        return chain.proceed(newRequest);
    }
}

private val moshi = Moshi.Builder()
    // First adapter used to skip top level field of incoming json.
    .add(Wrapped.ADAPTER_FACTORY)
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface BackendService {
    @GET("categories")
    suspend fun getCategories(): String
    @GET("events/{event}/categories")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getEventCategories(@Path("event") event : Int) : List<Category>
}

object BackendApi {
    val retrofitService : BackendService by lazy {
        retrofit.create(BackendService::class.java)
    }
}