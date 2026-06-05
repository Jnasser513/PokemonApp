package com.jnasser.core.testing

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType

fun pokemonTestData(
    id: Long = 1L,
    name: String = "bulbasaur"
) = Pokemon(
    id = id,
    name = name,
    imageUrl = "",
    height = 1,
    weight = 1,
    description = "",
    types = listOf(PokemonType(id, "grass")),
    stats = listOf(PokemonStat(id, 45, "hp"))
)
