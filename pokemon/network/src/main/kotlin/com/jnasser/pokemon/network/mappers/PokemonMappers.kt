package com.jnasser.pokemon.network.mappers

import com.jnasser.core.domain.pokemon.model.Pokemon
import com.jnasser.core.domain.pokemon.model.PokemonGenerationDetail
import com.jnasser.core.domain.pokemon.model.PokemonGeneration
import com.jnasser.core.domain.pokemon.model.PokemonStat
import com.jnasser.core.domain.pokemon.model.PokemonType
import com.jnasser.pokemon.network.model.PokemonDto
import com.jnasser.pokemon.network.model.PokemonGenerationDetailDto
import com.jnasser.pokemon.network.model.PokemonGenerationDto
import com.jnasser.pokemon.network.model.PokemonStatsDto
import com.jnasser.pokemon.network.model.PokemonTypesDto

fun List<PokemonGenerationDetailDto>.toPokemonList() = map(PokemonGenerationDetailDto::toPokemonGenerationDetail)

fun PokemonGenerationDto.toPokemonGeneration() = PokemonGeneration(
    pokemonGenerationDetail = pokemonSpecies.toPokemonList()
)

fun PokemonGenerationDetailDto.toPokemonGenerationDetail() = PokemonGenerationDetail(
    name = name,
    url = url
)

fun PokemonDto.toPokemon() = Pokemon(
    id = id,
    name = name,
    imageUrl = sprites.other.home.frontDefault,
    height = height,
    weight = weight,
    description = "",
    types = types.map { it.toPokemonType(id) },
    stats = stats.map { it.toPokemonStat(id) }
)

fun PokemonStatsDto.toPokemonStat(pokemonId: Long) = PokemonStat(
    pokemonId = pokemonId,
    baseStat = baseStat,
    name = stat.name
)

fun PokemonTypesDto.toPokemonType(pokemonId: Long) = PokemonType(
    pokemonId = pokemonId,
    type = type.name
)