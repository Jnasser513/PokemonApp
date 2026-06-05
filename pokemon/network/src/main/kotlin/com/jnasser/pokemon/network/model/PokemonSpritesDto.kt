package com.jnasser.pokemon.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpritesDto(
    val other: PokemonOtherSprites
)

@Serializable
data class PokemonOtherSprites(
    val home: PokemonOtherHomeSprites
)

@Serializable
data class PokemonOtherHomeSprites(
    @SerialName(value = "front_default")
    val frontDefault: String
)
