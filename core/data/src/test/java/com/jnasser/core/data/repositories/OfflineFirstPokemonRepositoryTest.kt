package com.jnasser.core.data.repositories

import com.jnasser.core.domain.coroutines.DispatcherProvider
import com.jnasser.core.domain.pokemon.datasources.LocalPokemonDataSource
import com.jnasser.core.domain.pokemon.datasources.RemotePokemonDataSource
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.pokemon.model.PokemonGenerationDetail
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import com.jnasser.core.testing.MainDispatcherRule
import com.jnasser.core.testing.pokemonTestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class OfflineFirstPokemonRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val remotePokemonDataSource = mockk<RemotePokemonDataSource>()
    private val localPokemonDataSource = mockk<LocalPokemonDataSource>()
    private val dispatcherProvider = mockk<DispatcherProvider>()

    @Test
    fun `toggle favorite removes when pokemon is already favorite`() = runTest {
        every { dispatcherProvider.io } returns StandardTestDispatcher(testScheduler)
        coEvery { localPokemonDataSource.isPokemonFavorite(1L) } returns true
        coEvery { localPokemonDataSource.removePokemonFavorite(1L) } returns Result.Success(true)

        val repository = createRepository()
        val result = repository.togglePokemonFavorite(1L)

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { localPokemonDataSource.removePokemonFavorite(1L) }
    }

    @Test
    fun `toggle favorite adds when pokemon is not favorite`() = runTest {
        every { dispatcherProvider.io } returns StandardTestDispatcher(testScheduler)
        coEvery { localPokemonDataSource.isPokemonFavorite(25L) } returns false
        coEvery { localPokemonDataSource.addPokemonFavorite(25L) } returns Result.Success(true)

        val repository = createRepository()
        val result = repository.togglePokemonFavorite(25L)

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { localPokemonDataSource.addPokemonFavorite(25L) }
    }

    @Test
    fun `generation fetch stores local pokemon details`() = runTest {
        every { dispatcherProvider.io } returns StandardTestDispatcher(testScheduler)
        coEvery { remotePokemonDataSource.getPokemonListByGeneration(1) } returns Result.Success(
            PokemonGeneration(
                listOf(
                    PokemonGenerationDetail("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
                )
            )
        )
        coEvery { remotePokemonDataSource.getPokemonDetail("bulbasaur") } returns Result.Success(
            pokemonTestData(1, "bulbasaur")
        )
        coEvery { localPokemonDataSource.upsertPokemon(any()) } returns Result.Success(true)
        coEvery { localPokemonDataSource.upsertPokemonStats(any()) } returns Result.Success(true)
        coEvery { localPokemonDataSource.upsertPokemonTypes(any()) } returns Result.Success(true)

        val repository = createRepository()
        val result = repository.getPokemonListByGeneration(1)

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { localPokemonDataSource.upsertPokemon(match { it.id == 1L }) }
        coVerify(exactly = 1) { localPokemonDataSource.upsertPokemonStats(any()) }
        coVerify(exactly = 1) { localPokemonDataSource.upsertPokemonTypes(any()) }
    }

    private fun createRepository() = OfflineFirstPokemonRepository(
        remotePokemonDataSource = remotePokemonDataSource,
        localPokemonDataSource = localPokemonDataSource,
        dispatcherProvider = dispatcherProvider
    )

}
