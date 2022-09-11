package com.saxomoose.frontend.services

import android.content.Context
import com.saxomoose.frontend.R
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
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

private const val BASE_URL = "http://demo.backend.test/api/"
private const val TOP_LEVEL_FIELD = "data"

abstract class BackendApi {
    companion object {
        @Volatile
        private var INSTANCE: BackendService? = null

        fun getService(context: Context, auth: Boolean): BackendService {
            return INSTANCE ?: synchronized(this) {
                var token: String? = null
                if (auth) {
                    val sharedPref = context.applicationContext.getSharedPreferences(
                        context.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                    )
                    token = sharedPref.getString(context.getString(R.string.token), null)
                }

                val instance = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(
                        if (auth) {
                            OkHttpClient.Builder()
                                .addInterceptor(ContentNegociationInterceptor())
                                .addInterceptor(TokenInterceptor(token))
                                //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                .build()
                        } else {
                            OkHttpClient.Builder()
                                .addInterceptor(ContentNegociationInterceptor())
                                //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                .build()
                        }
                    )
                    .baseUrl(BASE_URL)
                    .build()
                    .create(BackendService::class.java)
                INSTANCE = instance
                instance
            }
        }

        fun deallocateInstance() {
            INSTANCE = null
        }
    }
}

// Adds bearer token to headers.
class TokenInterceptor(
    private val token: String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}

// Adds Accept header. Content-type added automatically on POST requests.
class ContentNegociationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
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