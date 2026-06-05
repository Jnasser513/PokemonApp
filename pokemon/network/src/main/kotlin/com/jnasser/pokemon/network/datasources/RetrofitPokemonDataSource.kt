package com.jnasser.pokemon.network.datasources

import com.jnasser.core.data.networking.safeCall
import com.jnasser.core.domain.pokemon.datasources.RemotePokemonDataSource
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import com.jnasser.core.domain.util.result_handler.map
import com.jnasser.pokemon.network.PokemonApiService
import com.jnasser.pokemon.network.mappers.toPokemonGeneration

class RetrofitPokemonDataSource(
    private val service: PokemonApiService
): RemotePokemonDataSource {

    override suspend fun getPokemonListByGeneration(
        generation: Int
    ): Result<PokemonGeneration, DataError.Network> = safeCall {
        service.getPokemonListByGeneration(generation)
    }.map { it.toPokemonGeneration() }
}