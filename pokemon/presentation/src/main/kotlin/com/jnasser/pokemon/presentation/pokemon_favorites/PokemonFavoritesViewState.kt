package com.jnasser.pokemon.presentation.pokemon_favorites

import com.jnasser.pokemon.presentation.pokemon_list.model.PokemonListDataUi

data class PokemonFavoritesViewState(
    val isLoading: Boolean = true,
    val pokemonList: List<PokemonListDataUi> = emptyList()
)
