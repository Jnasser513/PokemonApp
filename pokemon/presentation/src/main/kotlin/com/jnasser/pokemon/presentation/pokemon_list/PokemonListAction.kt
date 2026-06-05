package com.jnasser.pokemon.presentation.pokemon_list

sealed interface PokemonListAction {
    data class OnSearch(val query: String): PokemonListAction
    data class OnPokemonDetail(val id: Int): PokemonListAction
    data class OnToggleFavorite(val id: Int): PokemonListAction
    data object OnOpenFavorites : PokemonListAction
}
