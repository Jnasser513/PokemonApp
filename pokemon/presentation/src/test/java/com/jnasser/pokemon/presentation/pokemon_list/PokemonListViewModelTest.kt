package com.jnasser.pokemon.presentation.pokemon_list

import app.cash.turbine.test
import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.pokemon.usecases.GetFavoritePokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.GetLocalPokemonListUseCase
import com.jnasser.core.domain.pokemon.usecases.GetPokemonByGenerationUseCase
import com.jnasser.core.domain.pokemon.usecases.TogglePokemonFavoriteUseCase
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import com.jnasser.core.testing.MainDispatcherRule
import com.jnasser.core.testing.pokemonTestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonByGenerationUseCase = mockk<GetPokemonByGenerationUseCase>()
    private val getLocalPokemonListUseCase = mockk<GetLocalPokemonListUseCase>()
    private val getFavoritePokemonListUseCase = mockk<GetFavoritePokemonListUseCase>()
    private val togglePokemonFavoriteUseCase = mockk<TogglePokemonFavoriteUseCase>()

    @Test
    fun `state marks favorites when local and favorite flows are combined`() = runTest {
        every { getLocalPokemonListUseCase.invoke() } returns flowOf(
            listOf(
                pokemonTestData(1, "bulbasaur"),
                pokemonTestData(2, "ivysaur")
            )
        )
        every { getFavoritePokemonListUseCase.invoke() } returns flowOf(
            listOf(pokemonTestData(2, "ivysaur"))
        )
        coEvery { getPokemonByGenerationUseCase.invoke(1) } returns Result.Success(
            PokemonGeneration(emptyList())
        )
        coEvery { togglePokemonFavoriteUseCase.invoke(any()) } returns Result.Success(true)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(2, viewModel.state.pokemonList.size)
        assertFalse(viewModel.state.pokemonList[0].isFavorite)
        assertTrue(viewModel.state.pokemonList[1].isFavorite)
    }

    @Test
    fun `on search updates query`() = runTest {
        stubSuccessfulDependencies()
        val viewModel = createViewModel()

        viewModel.onAction(PokemonListAction.OnSearch("saur"))

        assertEquals("saur", viewModel.state.searchQuery)
    }

    @Test
    fun `on toggle favorite delegates to use case`() = runTest {
        stubSuccessfulDependencies()
        coEvery { togglePokemonFavoriteUseCase.invoke(25L) } returns Result.Success(true)
        val viewModel = createViewModel()

        viewModel.onAction(PokemonListAction.OnToggleFavorite(25))
        advanceUntilIdle()

        coVerify(exactly = 1) { togglePokemonFavoriteUseCase.invoke(25L) }
    }

    @Test
    fun `get pokemon by generation error emits event`() = runTest {
        every { getLocalPokemonListUseCase.invoke() } returns flowOf(emptyList())
        every { getFavoritePokemonListUseCase.invoke() } returns flowOf(emptyList())
        coEvery { getPokemonByGenerationUseCase.invoke(1) } returns Result.Error(
            DataError.Network.UNKNOWN
        )
        coEvery { togglePokemonFavoriteUseCase.invoke(any()) } returns Result.Success(true)

        val viewModel = createViewModel()
        viewModel.events.test {
            advanceUntilIdle()
            val event = awaitItem()
            assertTrue(event is PokemonListEvent.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createViewModel() = PokemonListViewModel(
        getPokemonByGenerationUseCase = getPokemonByGenerationUseCase,
        getLocalPokemonListUseCase = getLocalPokemonListUseCase,
        getFavoritePokemonListUseCase = getFavoritePokemonListUseCase,
        togglePokemonFavoriteUseCase = togglePokemonFavoriteUseCase
    )

    private fun stubSuccessfulDependencies() {
        every { getLocalPokemonListUseCase.invoke() } returns flowOf(emptyList())
        every { getFavoritePokemonListUseCase.invoke() } returns flowOf(emptyList())
        coEvery { getPokemonByGenerationUseCase.invoke(1) } returns Result.Success(
            PokemonGeneration(emptyList())
        )
        coEvery { togglePokemonFavoriteUseCase.invoke(any()) } returns Result.Success(true)
    }

}
