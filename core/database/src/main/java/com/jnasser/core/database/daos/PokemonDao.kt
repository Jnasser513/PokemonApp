package com.jnasser.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jnasser.core.database.entities.PokemonEntity
import com.jnasser.core.database.entities.PokemonStatEntity
import com.jnasser.core.database.entities.PokemonTypeEntity
import com.jnasser.core.database.entities.PokemonWithTypesAndStats
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    /**
     * Returns all Pokémon with their associated types and stats.
     * This is a transactional query to fetch relations together.
     */
    @Transaction
    @Query("SELECT * FROM pokemon_entity")
    fun getAllWithTypesAndStats(): Flow<List<PokemonWithTypesAndStats>>

    @Transaction
    @Query("SELECT * FROM pokemon_entity WHERE id = :pokemonId")
    fun getPokemonWithTypesAndStats(pokemonId: Int): PokemonWithTypesAndStats

    @Upsert
    suspend fun upsertPokemon(pokemon: PokemonEntity): Long

    @Upsert
    suspend fun upsertPokemonTypes(types: List<PokemonTypeEntity>): List<Long>

    @Upsert
    suspend fun upsertPokemonStats(stats: List<PokemonStatEntity>): List<Long>
}