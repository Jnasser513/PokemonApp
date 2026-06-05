package com.jnasser.pokemon.presentation.com.jnasser.pokemon.presentation.di

import com.jnasser.pokemon.presentation.pokemon_list.PokemonListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokemonPresentationModule = module {
    // ViewModel
    viewModelOf(::PokemonListViewModel)
}