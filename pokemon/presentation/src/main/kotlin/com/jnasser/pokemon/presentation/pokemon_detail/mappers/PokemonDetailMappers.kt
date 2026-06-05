package com.jnasser.pokemon.presentation.pokemon_detail.mappers

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.pokemon.presentation.pokemon_detail.model.PokemonDetailDataUi
import com.jnasser.pokemon.presentation.pokemon_detail.model.StatContainerDataUi

fun PokemonStat.toPokemonStatDataUi() = StatContainerDataUi(
    name = name,
    value = baseStat,
    maxValue = 200 // This value is not defined in API doc
)

fun Pokemon.toPokemonDetailDatUi() = PokemonDetailDataUi(
    name = name,
    number = id.toString(),
    image = imageUrl,
    types = types,
    height = height.toString(),
    weight = weight.toString(),
    description = description,
    stats = stats
)