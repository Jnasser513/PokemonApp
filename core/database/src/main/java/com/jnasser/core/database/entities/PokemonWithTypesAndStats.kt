package com.jnasser.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonWithTypesAndStats(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val types: List<PokemonTypeEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val stats: List<PokemonStatEntity>
)
