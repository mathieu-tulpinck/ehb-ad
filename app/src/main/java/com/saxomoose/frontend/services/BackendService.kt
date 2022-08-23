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

private const val BASE_URL = "http://demo.backend.test/api/"
private const val TOP_LEVEL_FIELD = "data"
private const val REGISTER_METHOD_HASHCODE = 1738314101
private const val LOGIN_METHOD_HASHCODE = -1218096961

// Adds request headers.
class BackendInterceptor(
    private val token: String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        val request = builder.build()
        val tag = request.tag(Invocation::class.java)?.method()?.hashCode()
        // register and login requests should not have bearer token.
        if (tag == REGISTER_METHOD_HASHCODE || tag == LOGIN_METHOD_HASHCODE) {
            return chain.proceed(request)
        } else {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            return chain.proceed(newRequest)
        }
    }
}

private val moshi = Moshi.Builder()
    // First adapter used to skip top level field of incoming json. TODO: import functionality into project.
    .add(Wrapped.ADAPTER_FACTORY)
    // Date adapter.
    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).add(KotlinJsonAdapterFactory())
    .build()

interface BackendService {
    @POST("register")
    suspend fun register(
        @Body
        credentials: RequestBody
    ): retrofit2.Response<Unit>

    @POST("login")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun login(
        @Body
        credentials: RequestBody
    ): LoginResponse

    @GET("categories")
    suspend fun getCategories(): String

    @GET("users/{user}/events")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getUserEvents(
        @Path("user")
        userId: Int
    ): List<Event>

    @GET("events/{event}/items")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getEventItems(
        @Path("event")
        eventId: Int
    ): List<Item>

    @GET("events/{event}/categories")
    @Wrapped(path = [TOP_LEVEL_FIELD])
    suspend fun getEventCategories(
        @Path("event")
        eventId: Int
    ): List<Category>
}

// TODO: this class should be singleton.
class BackendApi() {
    private var _token: String? = null

    constructor(token: String) : this() {
        _token = token
    }

    val retrofitService: BackendService by lazy {
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                // OKHttpClient
                OkHttpClient.Builder()
                    .addInterceptor(BackendInterceptor(_token))
                    .build()
            )
            .baseUrl(BASE_URL)
            .build()
            .create(BackendService::class.java)
    }
}