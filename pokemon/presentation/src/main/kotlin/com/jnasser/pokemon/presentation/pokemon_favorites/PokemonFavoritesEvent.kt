package com.jnasser.pokemon.presentation.pokemon_favorites

import com.jnasser.core.presentation.ui.UiText

sealed interface PokemonFavoritesEvent {
    data class Error(val error: UiText) : PokemonFavoritesEvent
}
