package com.jnasser.pokemon.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonStatsDto(
    @SerialName(value = "base_stat")
    val baseStat: Int,
    val stat: PokemonStatDto
)

@Serializable
data class PokemonStatDto(
    val name: String
)
