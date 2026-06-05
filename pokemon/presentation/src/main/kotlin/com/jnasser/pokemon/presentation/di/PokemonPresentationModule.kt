package com.jnasser.pokemon.presentation.di

import com.jnasser.core.domain.pokemon.usecases.GetLocalPokemonByIdUseCase
import com.jnasser.core.domain.pokemon.usecases.GetLocalPokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.GetPokemonByGenerationUseCase
import com.jnasser.pokemon.presentation.pokemon_list.PokemonListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokemonPresentationModule = module {
    // ViewModel
    viewModelOf(::PokemonListViewModel)

    // Use cases
    singleOf(::GetPokemonByGenerationUseCase)
    singleOf(::GetLocalPokemonListUseCase)
    singleOf(::GetLocalPokemonByIdUseCase)
}