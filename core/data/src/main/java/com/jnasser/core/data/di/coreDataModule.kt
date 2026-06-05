package com.jnasser.core.data.di

import com.jnasser.core.data.coroutines.DispatcherProviderImpl
import com.jnasser.core.data.networking.NetworkFactory
import com.jnasser.core.data.repositories.OfflineFirstPokemonRepository
import com.jnasser.core.domain.coroutines.DispatcherProvider
import com.jnasser.core.domain.pokemon.repositories.PokemonRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }
    singleOf(::NetworkFactory)

    // Repository
    singleOf(::OfflineFirstPokemonRepository).bind<PokemonRepository>()
}
