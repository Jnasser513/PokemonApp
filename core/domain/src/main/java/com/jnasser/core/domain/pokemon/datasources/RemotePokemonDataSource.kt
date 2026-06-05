package com.jnasser.core.domain.pokemon.datasources

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result

/**
 * Data source interface for retrieving Pokémon data from a remote API.
 */
interface RemotePokemonDataSource {

    /**
     * Fetches a list of Pokémon from a specific generation.
     *
     * @param generation The generation number (e.g., 1 for Gen I).
     * @return A [Result] containing either the [PokemonGeneration] data
     *         or a [DataError.Network] in case of failure.
     */
    suspend fun getPokemonListByGeneration(
        generation: Int
    ): Result<PokemonGeneration, DataError.Network>

}
