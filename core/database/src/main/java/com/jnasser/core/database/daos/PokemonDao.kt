package com.jnasser.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jnasser.core.database.entities.FavoritePokemonEntity
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
    @Query(
        """
        SELECT pokemon_entity.* 
        FROM pokemon_entity
        INNER JOIN favorite_pokemon_entity 
            ON pokemon_entity.id = favorite_pokemon_entity.pokemonId
        ORDER BY favorite_pokemon_entity.createdAt DESC
        """
    )
    fun getFavoriteWithTypesAndStats(): Flow<List<PokemonWithTypesAndStats>>

    @Transaction
    @Query("SELECT * FROM pokemon_entity WHERE id = :pokemonId")
    fun getPokemonWithTypesAndStats(pokemonId: Int): PokemonWithTypesAndStats

    @Upsert
    suspend fun upsertPokemon(pokemon: PokemonEntity): Long

    @Upsert
    suspend fun upsertPokemonTypes(types: List<PokemonTypeEntity>): List<Long>

    @Upsert
    suspend fun upsertPokemonStats(stats: List<PokemonStatEntity>): List<Long>

    @Upsert
    suspend fun upsertFavoritePokemon(favoritePokemon: FavoritePokemonEntity): Long

    @Query("DELETE FROM favorite_pokemon_entity WHERE pokemonId = :pokemonId")
    suspend fun deleteFavoritePokemon(pokemonId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon_entity WHERE pokemonId = :pokemonId)")
    suspend fun isPokemonFavorite(pokemonId: Long): Boolean
}
