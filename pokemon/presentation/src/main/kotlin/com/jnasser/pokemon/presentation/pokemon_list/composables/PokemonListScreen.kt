package com.jnasser.pokemon.presentation.pokemon_list.composables

import PokemonAppScaffold
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.presentation.designsystem.components.PokedexSearchBar
import com.jnasser.core.presentation.designsystem.components.PokedexSearchBarConfig
import com.jnasser.core.presentation.designsystem.theme.PokemonAppIcons
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.core.presentation.ui.ObserveAsEvents
import com.jnasser.pokemon.presentation.R
import com.jnasser.pokemon.presentation.pokemon_list.PokemonListAction
import com.jnasser.pokemon.presentation.pokemon_list.PokemonListEvent
import com.jnasser.pokemon.presentation.pokemon_list.PokemonListViewModel
import com.jnasser.pokemon.presentation.pokemon_list.PokemonListViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreenRoot(
    viewModel: PokemonListViewModel = koinViewModel(),
    onPokemonDetail: (Int) -> Unit,
    onOpenFavorites: () -> Unit
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is PokemonListEvent.Error -> Toast.makeText(
                context,
                event.error.asString(context),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    PokemonListScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is PokemonListAction.OnPokemonDetail -> onPokemonDetail(action.id)
                PokemonListAction.OnOpenFavorites -> onOpenFavorites()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun PokemonListScreen(
    state: PokemonListViewState,
    onAction: (PokemonListAction) -> Unit
) {
    PokemonAppScaffold(
        topAppBar = { PokemonListTopBar(onOpenFavorites = { onAction(PokemonListAction.OnOpenFavorites) }) }
    ) { padding ->

        val searchBarState = rememberTextFieldState()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append(stringResource(R.string.hello))
                        }
                        append(stringResource(R.string.welcome))
                    },
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(20.dp))
                PokedexSearchBar(
                    textFieldState = searchBarState,
                    config = PokedexSearchBarConfig(
                        trailingIcon = PokemonAppIcons.Search,
                        onSearch = { query ->
                            onAction(PokemonListAction.OnSearch(query))
                        }
                    )
                )
                val filteredPokemonList = remember(state.pokemonList, state.searchQuery) {
                    val query = state.searchQuery.trim()
                    if (query.isBlank()) {
                        state.pokemonList
                    } else {
                        state.pokemonList.filter {
                            it.name.contains(query, ignoreCase = true) ||
                                it.number.contains(query, ignoreCase = true)
                        }
                    }
                }
                PokemonList(
                    pokemonList = filteredPokemonList,
                    onPokemonClick = { pokemonId ->
                        onAction(PokemonListAction.OnPokemonDetail(pokemonId))
                    },
                    onFavoriteClick = { pokemonId ->
                        onAction(PokemonListAction.OnToggleFavorite(pokemonId))
                    }
                )
            }
        }
    }
}

@Composable
fun PokemonListTopBar(
    modifier: Modifier = Modifier,
    onOpenFavorites: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = PokemonAppIcons.PokeBall,
                contentDescription = stringResource(R.string.pokeball),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.pokedex),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onOpenFavorites) {
            Icon(
                imageVector = PokemonAppIcons.FavoriteBorder,
                contentDescription = stringResource(R.string.favorites),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun PokemonListScreenLoadingPreview() {
    PokemonAppTheme {
        PokemonListScreen(
            PokemonListViewState(),
        ) { }
    }
}

@Preview
@Composable
private fun PokemonListScreenPreview() {
    PokemonAppTheme {
        PokemonListScreen(
            PokemonListViewState(isLoading = false),
        ) { }
    }
}
