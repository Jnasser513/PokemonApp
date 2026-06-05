package com.jnasser.pokemon.presentation.pokemon_list.mappers

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.pokemon.presentation.pokemon_list.model.PokemonListDataUi

fun Pokemon.toPokemonDataUi(isFavorite: Boolean = false) = PokemonListDataUi(
    name = name,
    imageUrl = imageUrl,
    number = id.toString(),
    isFavorite = isFavorite
)
