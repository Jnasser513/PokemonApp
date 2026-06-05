package com.jnasser.pokemon.presentation.pokemon_list

import com.jnasser.core.presentation.ui.UiText

sealed interface PokemonListEvent {
    data class Error(val error: UiText): PokemonListEvent
}