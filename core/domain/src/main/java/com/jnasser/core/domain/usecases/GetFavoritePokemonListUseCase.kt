package com.jnasser.core.domain.pokemon.usecases

import com.jnasser.core.domain.pokemon.repositories.PokemonRepository

class GetFavoritePokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke() = repository.getFavoritePokemons()
}
