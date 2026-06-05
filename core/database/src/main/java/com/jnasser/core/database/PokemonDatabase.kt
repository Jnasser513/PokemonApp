package com.jnasser.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jnasser.core.database.daos.PokemonDao
import com.jnasser.core.database.entities.PokemonEntity
import com.jnasser.core.database.entities.PokemonStatEntity
import com.jnasser.core.database.entities.PokemonTypeEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonTypeEntity::class,
        PokemonStatEntity::class
    ],
    version = RoomConstants.POKEMON_ROOM_DB_VERSION
)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}