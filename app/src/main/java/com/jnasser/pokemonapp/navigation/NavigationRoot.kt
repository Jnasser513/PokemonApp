package com.jnasser.pokemonapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.jnasser.pokemon.presentation.pokemon_detail.composables.PokemonDetailScreenRoot
import com.jnasser.pokemon.presentation.pokemon_list.composables.PokemonListScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object PokemonGraphRoute

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PokemonGraphRoute
    ) {


        pokemonGraph(navController)
    }
}

@Serializable
data object PokemonListRoute

@Serializable
data class PokemonDetailRoute(val pokemonId: Int)

private fun NavGraphBuilder.pokemonGraph(navController: NavHostController) {
    navigation<PokemonGraphRoute>(
        startDestination = PokemonListRoute
    ) {
        composable<PokemonListRoute> {
            PokemonListScreenRoot(
                onPokemonDetail = { pokemonId ->
                    navController.navigate(PokemonDetailRoute(pokemonId))
                }
            )
        }
        composable<PokemonDetailRoute> { backStackEntry ->
            val pokemonId = backStackEntry.toRoute<PokemonDetailRoute>().pokemonId

            PokemonDetailScreenRoot(
                pokemonId = pokemonId,
                onReturn = {
                    navController.navigateUp()
                }
            )
        }
    }
}