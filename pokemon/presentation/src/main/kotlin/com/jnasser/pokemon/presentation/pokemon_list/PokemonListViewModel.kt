package com.jnasser.pokemon.presentation.pokemon_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jnasser.core.domain.pokemon.usecases.GetFavoritePokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.GetLocalPokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.GetPokemonByGenerationUseCase
import com.jnasser.core.domain.pokemon.usecases.TogglePokemonFavoriteUseCase
import com.jnasser.core.domain.util.result_handler.Result
import com.jnasser.core.presentation.ui.asUiText
import com.jnasser.pokemon.presentation.pokemon_list.mappers.toPokemonDataUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonByGenerationUseCase: GetPokemonByGenerationUseCase,
    private val getLocalPokemonListUseCase: GetLocalPokemonListUseCase,
    private val getFavoritePokemonListUseCase: GetFavoritePokemonListUseCase,
    private val togglePokemonFavoriteUseCase: TogglePokemonFavoriteUseCase
): ViewModel() {

    var state by mutableStateOf(PokemonListViewState())
        private set

    private val eventChannel = Channel<PokemonListEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getPokemonByGeneration()
        combine(
            getLocalPokemonListUseCase.invoke(),
            getFavoritePokemonListUseCase.invoke()
        ) { pokemons, favorites ->
            val favoriteIds = favorites.mapTo(hashSetOf()) { it.id.toInt() }
            pokemons.map { pokemon ->
                pokemon.toPokemonDataUi(isFavorite = pokemon.id.toInt() in favoriteIds)
            }
        }.onEach { pokemonsDataUi ->
            state = state.copy(isLoading = false, pokemonList = pokemonsDataUi)
        }.launchIn(viewModelScope)
    }

    fun onAction(action: PokemonListAction) {
        when(action) {
            is PokemonListAction.OnSearch -> onSearch(action.query)
            is PokemonListAction.OnToggleFavorite -> toggleFavorite(action.id)
            else -> Unit
        }
    }

    private fun getPokemonByGeneration() {
        viewModelScope.launch {
            val result = getPokemonByGenerationUseCase.invoke(1)
            if(result is Result.Error) eventChannel.send(PokemonListEvent.Error(result.error.asUiText()))
        }
    }

    private fun onSearch(query: String) {
        state = state.copy(searchQuery = query)
    }

    private fun toggleFavorite(pokemonId: Int) {
        viewModelScope.launch {
            togglePokemonFavoriteUseCase(pokemonId.toLong())
        }
    }
}
