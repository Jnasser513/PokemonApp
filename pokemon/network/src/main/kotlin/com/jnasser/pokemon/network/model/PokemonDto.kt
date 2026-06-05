package com.jnasser.pokemon.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Long,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSpritesDto,
    val types: List<PokemonTypesDto>,
    val stats: List<PokemonStatsDto>
)
