package com.jnasser.pokemon.presentation.pokemon_detail.model

import com.jnasser.core.domain.pokemon.model.PokemonType

data class ImageContainerDataUi(
    val image: String,
    val types: List<PokemonType>
)