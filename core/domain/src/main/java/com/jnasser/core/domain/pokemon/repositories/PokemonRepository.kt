package com.jnasser.core.domain.pokemon.repositories

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing Pokémon data.
 * Acts as an abstraction between the domain layer and data sources.
 */
interface PokemonRepository {

    /**
     * Retrieves the list of Pokémon stored locally.
     *
     * @return A [Flow] emitting the list of Pokémon.
     */
    fun getPokemons(): Flow<List<Pokemon>>

    /**
     * Retrieves a single Pokémon stored locally by its ID.
     *
     * @return A [Flow] emitting the list of Pokémon.
     */
    fun getPokemonById(pokemonId: Int): Pokemon

    /**
     * Retrieves the list of Pokémon for a specific generation.
     *
     * @param generation The generation number to retrieve (e.g., 1 for Gen I).
     * @return A [Result] containing either the [PokemonGeneration] data or a [DataError] on failure.
     */
    suspend fun getPokemonListByGeneration(
        generation: Int
    ): Result<PokemonGeneration, DataError>
}
