package com.jnasser.pokemon.presentation.pokemon_detail

import com.jnasser.pokemon.presentation.pokemon_detail.model.PokemonDetailDataUi

data class PokemonDetailViewState(
    val isLoading: Boolean = true,
    val pokemonDetail: PokemonDetailDataUi = PokemonDetailDataUi()
)
