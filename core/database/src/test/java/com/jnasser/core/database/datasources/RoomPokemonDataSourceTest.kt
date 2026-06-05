package com.jnasser.core.database.datasources

import android.database.sqlite.SQLiteFullException
import com.jnasser.core.database.PokemonDatabase
import com.jnasser.core.database.daos.PokemonDao
import com.jnasser.core.database.entities.FavoritePokemonEntity
import com.jnasser.core.database.entities.PokemonEntity
import com.jnasser.core.database.entities.PokemonStatEntity
import com.jnasser.core.database.entities.PokemonTypeEntity
import com.jnasser.core.database.entities.PokemonWithTypesAndStats
import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.util.error_handler.DataError
import com.jnasser.core.domain.util.result_handler.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RoomPokemonDataSourceTest {

    private val pokemonDatabase = mockk<PokemonDatabase>(relaxed = true)
    private val pokemonDao = mockk<PokemonDao>()
    private val dataSource = RoomPokemonDataSource(pokemonDatabase, pokemonDao)

    @Test
    fun `get favorite pokemons maps entity graph to domain`() = kotlinx.coroutines.test.runTest {
        every { pokemonDao.getFavoriteWithTypesAndStats() } returns flowOf(
            listOf(
                PokemonWithTypesAndStats(
                    pokemon = PokemonEntity(
                        id = 4,
                        name = "charmander",
                        imageUrl = "img",
                        height = 6,
                        weight = 85,
                        description = "fire lizard"
                    ),
                    types = listOf(PokemonTypeEntity(4, "fire")),
                    stats = listOf(PokemonStatEntity(4, 39, "hp"))
                )
            )
        )

        val result = dataSource.getFavoritePokemons()

        val items = result.first()
        assertEquals(1, items.size)
        assertEquals("charmander", items.first().name)
        assertEquals("fire", items.first().types.first().type)
    }

    @Test
    fun `add favorite persists entity`() = kotlinx.coroutines.test.runTest {
        coEvery { pokemonDao.upsertFavoritePokemon(any()) } returns 1L

        val result = dataSource.addPokemonFavorite(25L)

        assertTrue(result is Result.Success)
        val favoriteSlot = slot<FavoritePokemonEntity>()
        coVerify(exactly = 1) {
            pokemonDao.upsertFavoritePokemon(capture(favoriteSlot))
        }
        assertEquals(25L, favoriteSlot.captured.pokemonId)
    }

    @Test
    fun `add favorite returns disk full error when room throws`() = kotlinx.coroutines.test.runTest {
        coEvery { pokemonDao.upsertFavoritePokemon(any()) } throws SQLiteFullException()

        val result = dataSource.addPokemonFavorite(25L)

        assertTrue(result is Result.Error)
        assertEquals(DataError.Local.DISK_FULL, (result as Result.Error).error)
    }

    @Test
    fun `remove favorite delegates to dao`() = kotlinx.coroutines.test.runTest {
        coEvery { pokemonDao.deleteFavoritePokemon(25L) } returns Unit

        val result = dataSource.removePokemonFavorite(25L)

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { pokemonDao.deleteFavoritePokemon(25L) }
    }

    @Test
    fun `is favorite delegates to dao`() = kotlinx.coroutines.test.runTest {
        coEvery { pokemonDao.isPokemonFavorite(7L) } returns true

        val result = dataSource.isPokemonFavorite(7L)

        assertTrue(result)
    }

    @Test
    fun `upsert pokemons writes graph in a single transaction`() = kotlinx.coroutines.test.runTest {
        every { pokemonDatabase.runInTransaction(any<Runnable>()) } answers {
            firstArg<Runnable>().run()
        }
        coEvery { pokemonDao.upsertPokemon(any()) } returns 1L
        coEvery { pokemonDao.upsertPokemonStats(any()) } returns listOf(1L)
        coEvery { pokemonDao.upsertPokemonTypes(any()) } returns listOf(1L)

        val result = dataSource.upsertPokemons(
            listOf(
                com.jnasser.core.testing.pokemonTestData(1, "bulbasaur")
            )
        )

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) { pokemonDao.upsertPokemon(any()) }
        coVerify(exactly = 1) { pokemonDao.upsertPokemonStats(any()) }
        coVerify(exactly = 1) { pokemonDao.upsertPokemonTypes(any()) }
    }

}
