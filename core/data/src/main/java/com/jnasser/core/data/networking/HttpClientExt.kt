package com.jnasser.core.data.networking

import retrofit2.Retrofit

inline fun <reified T> Retrofit.createService(): T = create(T::class.java)
