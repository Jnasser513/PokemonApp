package com.jnasser.pokemon.network.di

import com.jnasser.core.data.networking.NetworkFactory
import com.jnasser.core.domain.pokemon.datasources.RemotePokemonDataSource
import com.jnasser.pokemon.network.PokemonApiService
import com.jnasser.pokemon.network.datasources.RetrofitPokemonDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pokemonNetworkModule = module {
    single<PokemonApiService> {
        NetworkFactory().createService(PokemonApiService::class.java)
    }

    singleOf(::RetrofitPokemonDataSource).bind<RemotePokemonDataSource>()
}