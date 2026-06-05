package com.jnasser.core.database.di

import androidx.room.Room
import com.jnasser.core.database.PokemonDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val POKEMON_ROOM_DB_NAME = "pokemon.db"

val coreDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PokemonDatabase::class.java,
            POKEMON_ROOM_DB_NAME
        ).build()
    }

    single { get<PokemonDatabase>().pokemonDao }
}