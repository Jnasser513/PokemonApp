package com.jnasser.core.database.datasources

import android.database.sqlite.SQLiteFullException
import com.jnasser.core.database.daos.PokemonDao
import com.jnasser.core.database.mappers.toPokemon
import com.jnasser.core.database.mappers.toPokemonEntity
import com.jnasser.core.database.mappers.toPokemonStatEntity
import com.jnasser.core.database.mappers.toPokemonTypeEntity
import com.jnasser.core.domain.pokemon.datasources.LocalPokemonDataSource
import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Local data source implementation that uses Room to store and retrieve Pokémon data.
 * Handles persistence of Pokémon, their types, and stats.
 */
class RoomPokemonDataSource(
    private val pokemonDao: PokemonDao
) : LocalPokemonDataSource {

    /**
     * Retrieves all Pokémon stored in the local database along with their types and stats.
     *
     * @return A flow of Pokémon list mapped to the domain model.
     */
    override fun getPokemons(): Flow<List<Pokemon>> {
        return pokemonDao.getAllWithTypesAndStats()
            .map { pokemonEntities ->
                pokemonEntities.map { it.toPokemon() }
            }
    }

    /**
     * Retrieves a single Pokémon from the local database by its ID,
     * including its types and stats, and maps it to the domain model.
     *
     * @param pokemonId The ID of the Pokémon to retrieve.
     * @return A [Flow] emitting the [Pokemon] object with full details.
     */
    override fun getPokemonById(pokemonId: Int): Pokemon {
        return pokemonDao.getPokemonWithTypesAndStats(pokemonId).toPokemon()
    }

    /**
     * Inserts or updates a Pokémon entity in the database.
     *
     * @param pokemon The domain model to persist.
     * @return [Result] indicating success or disk full error.
     *          If the result contains -1L, it means an error occurred while saving to Room.
     */
    override suspend fun upsertPokemon(pokemon: Pokemon): Result<Boolean, DataError.Local> {
        return try {
            val entity = pokemon.toPokemonEntity()
            val result = pokemonDao.upsertPokemon(entity)
            Result.Success(result != -1L)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    /**
     * Inserts or updates a list of Pokémon types in the database.
     *
     * @param types The list of domain model types to persist.
     * @return [Result] indicating partial success or disk full error.
     *          If the result contains at least one -1L, it means an error occurred while saving to Room.
     */
    override suspend fun upsertPokemonTypes(types: List<PokemonType>): Result<Boolean, DataError.Local> {
        return try {
            val entity = types.map { it.toPokemonTypeEntity() }
            val result = pokemonDao.upsertPokemonTypes(entity)
            Result.Success(result.any { it != -1L })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    /**
     * Inserts or updates a list of Pokémon stats in the database.
     *
     * @param stats The list of domain model stats to persist.
     * @return [Result] indicating partial success or disk full error.
     *          If the result contains at least one -1L, it means an error occurred while saving to Room.
     */
    override suspend fun upsertPokemonStats(stats: List<PokemonStat>): Result<Boolean, DataError.Local> {
        return try {
            val entity = stats.map { it.toPokemonStatEntity() }
            val result = pokemonDao.upsertPokemonStats(entity)
            Result.Success(result.any { it != -1L })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}
