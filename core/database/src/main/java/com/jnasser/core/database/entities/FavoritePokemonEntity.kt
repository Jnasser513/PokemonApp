package com.jnasser.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_pokemon_entity",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoritePokemonEntity(
    @PrimaryKey
    val pokemonId: Long,
    val createdAt: Long
)
