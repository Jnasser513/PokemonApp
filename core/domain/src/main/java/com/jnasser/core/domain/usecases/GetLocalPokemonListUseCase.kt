package com.jnasser.core.domain.pokemon.usecases

import com.jnasser.core.domain.pokemon.repositories.PokemonRepository

class GetLocalPokemonListUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke() = repository.getPokemons()
}