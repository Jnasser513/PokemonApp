package com.jnasser.core.data.di

import com.jnasser.core.data.coroutines.DispatcherProviderImpl
import com.jnasser.core.data.networking.HttpClientFactory
import com.jnasser.core.domain.coroutines.DispatcherProvider
import org.koin.dsl.module

val coreDataModule = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }
    single {
        HttpClientFactory.createRetrofit("https://pokeapi.co/api/v2/")
    }
}
