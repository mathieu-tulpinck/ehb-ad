package com.saxomoose.frontend.services

import com.saxomoose.frontend.models.Category
import com.saxomoose.frontend.models.Event
import com.saxomoose.frontend.models.Item
import com.saxomoose.frontend.ui.auth.login.LoginResponse
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

// TODO get token from login activity.
private const val BASE_URL = "http://demo.backend.test/api/"
private const val token = "1|cYpZHYCdcL5HDY0LsVd1PriWMTZwSkhjeeoffEhY"
private const val TOP_LEVEL_FIELD = "data"

val client = OkHttpClient.Builder()
    .addInterceptor(BackendInterceptor())
    .build()

// Adds request headers.
class BackendInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        val request = builder.build()
        val tag = request.tag(Invocation::class.java)?.method()?.toString()
        // register and login requests should not have bearer token.
        if (tag != "public abstract java.lang.Object com.saxomoose.frontend.services.BackendService.register(java.lang.String,kotlin.coroutines.Continuation)") {
            val newRequest  = request.newBuilder().addHeader("Authorization", "Bearer $token").build()

            return chain.proceed(newRequest);
        }

        return chain.proceed(request)
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
    @POST("register")
    suspend fun register(@Body credentials : RequestBody) : retrofit2.Response<Unit>
    @POST("login")
    suspend fun login(@Body credentials : RequestBody) : retrofit2.Response<LoginResponse>
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