package com.jnasser.core.data.repositories

import com.jnasser.core.domain.coroutines.DispatcherProvider
import com.jnasser.core.domain.pokemon.datasources.LocalPokemonDataSource
import com.jnasser.core.domain.pokemon.datasources.RemotePokemonDataSource
import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.pokemon.repositories.PokemonRepository
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext

/**
 * Repository implementation that follows an offline-first strategy.
 * Fetches Pokémon data from the remote source and persists it locally.
 * Reads data from the local database when available.
 */
class OfflineFirstPokemonRepository(
    private val remotePokemonDataSource: RemotePokemonDataSource,
    private val localPokemonDataSource: LocalPokemonDataSource,
    private val dispatcherProvider: DispatcherProvider
) : PokemonRepository {

    /**
     * Retrieves a list of Pokémon from the local database.
     */
    override fun getPokemons(): Flow<List<Pokemon>> = localPokemonDataSource.getPokemons()

    /**
     * Retrieves a list of Pokémon from the local database by its ID.
     */
    override fun getPokemonById(pokemonId: Int): Pokemon =
        localPokemonDataSource.getPokemonById(pokemonId)

    /**
     * Fetches a list of Pokémon for a specific generation from the remote API.
     * If successful, it retrieves each Pokémon's detail concurrently and stores it locally.
     *
     * @param generation The generation number (e.g., 1 for Gen I).
     * @return [Result] containing the [PokemonGeneration] or a [DataError] if the request fails.
     */
    override suspend fun getPokemonListByGeneration(
        generation: Int
    ): Result<PokemonGeneration, DataError> = withContext(dispatcherProvider.io) {
        when (val result = remotePokemonDataSource.getPokemonListByGeneration(generation)) {
            is Result.Error -> Result.Error(result.error) // Return immediately if the generation list fails

            is Result.Success -> {
                val pokemons = result.data.pokemonGenerationDetail

                // Use Semaphore to control concurrent requests and avoid ui to freeze
                val semaphore = Semaphore(CONCURRENT_REQUESTS)

                // Launch concurrent requests to fetch Pokémon details
                pokemons.map { pokemon ->
                    async {
                        semaphore.withPermit {
                            val pokemonResult = remotePokemonDataSource.getPokemonDetail(pokemon.name)

                            if(pokemonResult is Result.Success) upsertPokemonDetail(pokemonResult.data)
                        }
                    }
                }.awaitAll()

                Result.Success(result.data)
            }
        }
    }

    /**
     * Persists the given Pokémon detail into the local database,
     * including its types and stats.
     */
    private suspend fun upsertPokemonDetail(pokemonDetail: Pokemon) {
        localPokemonDataSource.upsertPokemon(pokemonDetail)
        localPokemonDataSource.upsertPokemonStats(pokemonDetail.stats)
        localPokemonDataSource.upsertPokemonTypes(pokemonDetail.types)
    }

    companion object {
        private const val CONCURRENT_REQUESTS = 5
    }
}
