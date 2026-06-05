package com.jnasser.pokemon.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonGenerationDetailDto(
    val name: String,
    val url: String
)
