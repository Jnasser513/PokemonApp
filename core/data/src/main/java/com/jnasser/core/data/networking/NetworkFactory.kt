package com.jnasser.core.data.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jnasser.core.database.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NetworkFactory {

    private val baseUrl = BuildConfig.BASE_URL

    private val contentType = "application/json".toMediaType()

    // Ignore unknown Json keys to avoid crashes
    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        val logging = HttpLoggingInterceptor { message ->
            Timber.d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(
                json.asConverterFactory(contentType)
            )
            .build()

        return retrofit.create(serviceClass)
    }
}