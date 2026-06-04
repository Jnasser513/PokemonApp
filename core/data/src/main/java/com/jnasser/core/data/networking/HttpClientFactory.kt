package com.jnasser.core.data.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object HttpClientFactory {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    fun createRetrofit(
        baseUrl: String,
        interceptors: List<Interceptor> = emptyList(),
    ): Retrofit {
        val client = OkHttpClient.Builder().apply {
            interceptors.forEach(::addInterceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory("application/json".toMediaType().let(json::asConverterFactory))
            .build()
    }
}
