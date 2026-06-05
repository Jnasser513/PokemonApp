package com.jnasser.pokemon.network

import com.jnasser.core.domain.network.APIConstants
import com.jnasser.pokemon.network.model.PokemonGenerationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET(APIConstants.GET_POKEMONS_BY_GENERATION)
    suspend fun getPokemonListByGeneration(
        @Path("generation") generation: Int
    ): Response<PokemonGenerationDto>

}