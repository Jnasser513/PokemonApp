package com.jnasser.pokemon.presentation.pokemon_list.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jnasser.pokemon.presentation.pokemon_list.model.PokemonListDataUi

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    pokemonList: List<PokemonListDataUi>,
    onPokemonClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(pokemonList) { item ->
            PokemonItem(
                pokemon = item,
                onClick = { number ->
                    number.toIntOrNull()?.let { onPokemonClick(it) }
                }
            )
        }
    }
}