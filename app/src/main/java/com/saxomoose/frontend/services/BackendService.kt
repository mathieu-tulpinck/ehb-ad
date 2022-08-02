package com.saxomoose.frontend.services

import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.models.Event
import com.saxomoose.frontend.models.Item
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

// TODO get token from login activity.
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
    // First adapter used to skip top level field of incoming json. TODO import functionality into project.
    .add(Wrapped.ADAPTER_FACTORY)
    // Date adapter.
    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
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
    @GET("users/{user}/events")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getUserEvents(@Path("user") userId : Int) : List<Event>
    @GET("events/{event}/items")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getEventItems(@Path("event") eventId : Int) : List<Item>
    @GET("events/{event}/categories")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getEventCategories(@Path("event") eventId : Int) : List<Category>
}

object BackendApi {
    val retrofitService : BackendService by lazy {
        retrofit.create(BackendService::class.java)
    }
}