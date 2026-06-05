package com.jnasser.core.domain.pokemon.usecases

import com.jnasser.core.domain.coroutines.DispatcherProvider
import com.jnasser.core.domain.pokemon.repositories.PokemonRepository
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import kotlinx.coroutines.withContext

class TogglePokemonFavoriteUseCase(
    private val repository: PokemonRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(pokemonId: Long): Result<Boolean, DataError> = withContext(dispatcherProvider.io) {
        repository.togglePokemonFavorite(pokemonId)
    }
}
