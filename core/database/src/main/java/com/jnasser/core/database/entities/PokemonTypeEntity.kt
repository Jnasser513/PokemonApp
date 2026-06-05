package com.jnasser.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Represents a base type (e.g. "fire", "poison") for a specific Pokémon.
 * Uses a composite primary key (pokemonId + type) to allow multiple types per Pokémon.
 * Types are deleted automatically when the related Pokémon is removed.
 */
@Entity(
    tableName = "pokemon_type_entity",
    primaryKeys = ["pokemonId", "type"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonTypeEntity(
    val pokemonId: Long,
    val type: String
)
