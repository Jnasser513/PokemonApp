package com.jnasser.core.domain.pokemon.model

data class Pokemon(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val description: String,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>
)
