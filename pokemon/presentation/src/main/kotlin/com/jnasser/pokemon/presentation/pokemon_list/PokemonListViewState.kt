package com.jnasser.pokemon.presentation.pokemon_list

import com.jnasser.pokemon.presentation.pokemon_list.model.PokemonListDataUi

data class PokemonListViewState(
    val isLoading: Boolean = true,
    val pokemonList: List<PokemonListDataUi> = emptyList(),
    val searchQuery: String = ""
)
