package com.jnasser.pokemon.presentation.pokemon_favorites

import app.cash.turbine.test
import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.usecases.GetFavoritePokemonListUseCase
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
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonFavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getFavoritePokemonListUseCase = mockk<GetFavoritePokemonListUseCase>()
    private val togglePokemonFavoriteUseCase = mockk<TogglePokemonFavoriteUseCase>()

    @Test
    fun `state is populated from favorites flow`() = runTest {
        every { getFavoritePokemonListUseCase.invoke() } returns flowOf(
            listOf(
                pokemonTestData(4, "charmander"),
                pokemonTestData(7, "squirtle")
            )
        )
        coEvery { togglePokemonFavoriteUseCase.invoke(any()) } returns Result.Success(true)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(2, viewModel.state.pokemonList.size)
        assertTrue(viewModel.state.pokemonList.all { it.isFavorite })
    }

    @Test
    fun `on toggle favorite error emits event`() = runTest {
        every { getFavoritePokemonListUseCase.invoke() } returns flowOf(emptyList())
        coEvery { togglePokemonFavoriteUseCase.invoke(4L) } returns Result.Error(
            DataError.Local.DISK_FULL
        )

        val viewModel = createViewModel()
        viewModel.events.test {
            viewModel.onAction(PokemonFavoritesAction.OnToggleFavorite(4))
            advanceUntilIdle()

            val event = awaitItem()
            assertTrue(event is PokemonFavoritesEvent.Error)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { togglePokemonFavoriteUseCase.invoke(4L) }
    }

    private fun createViewModel() = PokemonFavoritesViewModel(
        getFavoritePokemonListUseCase = getFavoritePokemonListUseCase,
        togglePokemonFavoriteUseCase = togglePokemonFavoriteUseCase
    )
}
