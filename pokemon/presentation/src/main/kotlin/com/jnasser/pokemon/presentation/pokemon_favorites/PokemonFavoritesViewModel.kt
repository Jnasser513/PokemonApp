package com.jnasser.pokemon.presentation.pokemon_favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jnasser.core.domain.pokemon.usecases.GetFavoritePokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.TogglePokemonFavoriteUseCase
import com.jnasser.core.domain.util.result_handler.Result
import com.jnasser.core.presentation.ui.asUiText
import com.jnasser.pokemon.presentation.pokemon_list.mappers.toPokemonDataUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PokemonFavoritesViewModel(
    private val getFavoritePokemonListUseCase: GetFavoritePokemonListUseCase,
    private val togglePokemonFavoriteUseCase: TogglePokemonFavoriteUseCase
) : ViewModel() {

    var state by mutableStateOf(PokemonFavoritesViewState())
        private set

    private val eventChannel = Channel<PokemonFavoritesEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getFavoritePokemons()
    }

    fun onAction(action: PokemonFavoritesAction) {
        when (action) {
            is PokemonFavoritesAction.OnToggleFavorite -> toggleFavorite(action.id)
            else -> Unit
        }
    }

    private fun getFavoritePokemons() {
        getFavoritePokemonListUseCase.invoke()
            .onEach { pokemons ->
                state = state.copy(
                    isLoading = false,
                    pokemonList = pokemons.map { it.toPokemonDataUi(isFavorite = true) }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun toggleFavorite(pokemonId: Int) {
        viewModelScope.launch {
            when (val result = togglePokemonFavoriteUseCase(pokemonId.toLong())) {
                is Result.Error -> eventChannel.send(PokemonFavoritesEvent.Error(result.error.asUiText()))
                else -> Unit
            }
        }
    }
}
