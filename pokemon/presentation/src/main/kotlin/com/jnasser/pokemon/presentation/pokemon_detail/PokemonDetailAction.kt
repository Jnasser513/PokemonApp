package com.jnasser.pokemon.presentation.pokemon_detail

sealed interface PokemonDetailAction {
    data object OnReturn: PokemonDetailAction
}