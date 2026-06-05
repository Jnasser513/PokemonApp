package com.jnasser.core.domain.pokemon.datasources

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import kotlinx.coroutines.flow.Flow

interface LocalPokemonDataSource {
    fun getPokemons(): Flow<List<Pokemon>>
    fun getPokemonById(pokemonId: Int): Pokemon
    suspend fun upsertPokemon(pokemon: Pokemon): Result<Boolean, DataError.Local>
    suspend fun upsertPokemonTypes(types: List<PokemonType>): Result<Boolean, DataError.Local>
    suspend fun upsertPokemonStats(stats: List<PokemonStat>): Result<Boolean, DataError.Local>
}