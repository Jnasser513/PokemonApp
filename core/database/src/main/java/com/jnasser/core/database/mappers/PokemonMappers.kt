package com.jnasser.core.database.mappers

import com.jnasser.core.database.entities.PokemonEntity
import com.jnasser.core.database.entities.PokemonStatEntity
import com.jnasser.core.database.entities.PokemonTypeEntity
import com.jnasser.core.database.entities.PokemonWithTypesAndStats
import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType

fun PokemonWithTypesAndStats.toPokemon() = Pokemon(
    id = pokemon.id,
    name = pokemon.name,
    imageUrl = pokemon.imageUrl,
    height = pokemon.height,
    weight = pokemon.weight,
    description = pokemon.description,
    types = types.map { it.toPokemonType() },
    stats = stats.map { it.toPokemonStat() }
)

fun PokemonTypeEntity.toPokemonType() = PokemonType(
    pokemonId = pokemonId,
    type = type
)

fun PokemonStatEntity.toPokemonStat() = PokemonStat(
    pokemonId = pokemonId,
    name = name,
    baseStat = baseStat
)

fun Pokemon.toPokemonEntity() = PokemonEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    height = height,
    weight = weight,
    description = description
)

fun PokemonType.toPokemonTypeEntity() = PokemonTypeEntity(
    pokemonId = pokemonId,
    type = type
)

fun PokemonStat.toPokemonStatEntity() = PokemonStatEntity(
    pokemonId = pokemonId,
    baseStat = baseStat,
    name = name
)