package com.jnasser.core.domain.pokemon.usecases

import com.jnasser.core.domain.coroutines.DispatcherProvider
import com.jnasser.core.domain.pokemon.repositories.PokemonRepository
import kotlinx.coroutines.withContext

class GetLocalPokemonByIdUseCase(
    private val repository: PokemonRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(pokemonId: Int) = withContext(dispatcherProvider.io) {
        repository.getPokemonById(pokemonId)
    }

}