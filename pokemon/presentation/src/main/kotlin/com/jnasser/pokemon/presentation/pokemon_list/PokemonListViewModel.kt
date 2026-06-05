package com.jnasser.pokemon.presentation.pokemon_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jnasser.core.domain.pokemon.usecases.GetLocalPokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.GetPokemonByGenerationUseCase
import com.jnasser.core.domain.util.result_handler.Result
import com.jnasser.core.presentation.ui.asUiText
import com.jnasser.pokemon.presentation.pokemon_list.mappers.toPokemonDataUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonByGenerationUseCase: GetPokemonByGenerationUseCase,
    private val getLocalPokemonListUseCase: GetLocalPokemonListUseCase
): ViewModel() {

    var state by mutableStateOf(PokemonListViewState())
        private set

    private val eventChannel = Channel<PokemonListEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getPokemonByGeneration()
        viewModelScope.launch {
            getLocalPokemonListUseCase.invoke().onEach { pokemons ->
                val pokemonsDataUi = pokemons.map { it.toPokemonDataUi() }
                state = state.copy(isLoading = false, pokemonList = pokemonsDataUi)
            }.launchIn(viewModelScope)
        }
    }

    fun onAction(action: PokemonListAction) {
        when(action) {
            is PokemonListAction.OnSearch -> onSearch(action.query)
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
}