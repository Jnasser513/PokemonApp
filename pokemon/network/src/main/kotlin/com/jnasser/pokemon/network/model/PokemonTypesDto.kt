package com.jnasser.pokemon.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypesDto(
    val type: PokemonTypeDto
)

@Serializable
data class PokemonTypeDto(
    val name: String
)
