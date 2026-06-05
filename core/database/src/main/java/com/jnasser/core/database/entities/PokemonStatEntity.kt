package com.jnasser.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Represents a base stat (e.g. "attack", "defense") for a specific Pokémon.
 * Uses a composite primary key (pokemonId + name) to allow multiple stats per Pokémon.
 * Stats are deleted automatically when the related Pokémon is removed.
 */
@Entity(
    tableName = "pokemon_stat_entity",
    primaryKeys = ["pokemonId", "name"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonStatEntity(
    val pokemonId: Long,
    val baseStat: Int,
    val name: String
)
