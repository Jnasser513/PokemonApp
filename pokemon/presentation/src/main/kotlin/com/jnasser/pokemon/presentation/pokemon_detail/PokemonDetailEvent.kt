package com.jnasser.pokemon.presentation.pokemon_detail

import com.jnasser.core.presentation.ui.UiText

sealed interface PokemonDetailEvent {
    data class Error(val error: UiText): PokemonDetailEvent
}