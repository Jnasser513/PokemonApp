package com.jnasser.pokemon.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonGenerationDto(
    @SerialName(value = "pokemon_species")
    val pokemonSpecies: List<PokemonGenerationDetailDto>
)
