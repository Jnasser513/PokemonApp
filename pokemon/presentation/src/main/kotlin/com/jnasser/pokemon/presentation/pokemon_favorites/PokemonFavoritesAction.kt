package com.jnasser.pokemon.presentation.pokemon_favorites

sealed interface PokemonFavoritesAction {
    data class OnPokemonDetail(val id: Int) : PokemonFavoritesAction
    data class OnToggleFavorite(val id: Int) : PokemonFavoritesAction
    data object OnBack : PokemonFavoritesAction
}
