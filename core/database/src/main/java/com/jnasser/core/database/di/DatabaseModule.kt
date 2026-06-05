package com.jnasser.core.database.di

import androidx.room.Room
import com.jnasser.core.database.PokemonDatabase
import com.jnasser.core.database.datasources.RoomPokemonDataSource
import com.jnasser.core.database.migrations.MIGRATION_1_2
import com.jnasser.core.domain.pokemon.datasources.LocalPokemonDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val POKEMON_ROOM_DB_NAME = "pokemon.db"

val coreDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PokemonDatabase::class.java,
            POKEMON_ROOM_DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    // DAOs
    single { get<PokemonDatabase>().pokemonDao }

    // Datasource
    singleOf(::RoomPokemonDataSource).bind<LocalPokemonDataSource>()
}
