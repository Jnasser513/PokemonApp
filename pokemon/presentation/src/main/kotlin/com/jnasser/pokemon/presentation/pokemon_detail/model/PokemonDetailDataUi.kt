package com.jnasser.pokemon.presentation.pokemon_detail.model

import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType

data class PokemonDetailDataUi(
    val name: String = "",
    val number: String = "",
    val image: String = "",
    val types: List<PokemonType> = emptyList(),
    val height: String = "",
    val weight: String = "",
    val description: String? = null,
    val stats: List<PokemonStat> = emptyList()
)
