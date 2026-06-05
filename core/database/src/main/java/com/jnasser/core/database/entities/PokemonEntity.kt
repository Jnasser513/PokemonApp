package com.jnasser.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_entity")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val description: String
)
