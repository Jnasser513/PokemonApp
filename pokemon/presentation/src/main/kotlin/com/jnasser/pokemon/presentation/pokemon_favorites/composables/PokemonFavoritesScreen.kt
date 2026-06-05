package com.jnasser.pokemon.presentation.pokemon_favorites.composables

import PokemonAppScaffold
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.presentation.designsystem.components.PokedexTopAppBar
import com.jnasser.core.presentation.designsystem.components.PokedexTopAppBarConfig
import com.jnasser.core.presentation.designsystem.components.PokemonAppLoading
import com.jnasser.core.presentation.designsystem.theme.PokemonAppIcons
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.core.presentation.designsystem.theme.PokemonColors
import com.jnasser.core.presentation.ui.ObserveAsEvents
import com.jnasser.pokemon.presentation.R
import com.jnasser.pokemon.presentation.pokemon_favorites.PokemonFavoritesAction
import com.jnasser.pokemon.presentation.pokemon_favorites.PokemonFavoritesEvent
import com.jnasser.pokemon.presentation.pokemon_favorites.PokemonFavoritesViewModel
import com.jnasser.pokemon.presentation.pokemon_favorites.PokemonFavoritesViewState
import com.jnasser.pokemon.presentation.pokemon_list.composables.PokemonList
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonFavoritesScreenRoot(
    viewModel: PokemonFavoritesViewModel = koinViewModel(),
    onPokemonDetail: (Int) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is PokemonFavoritesEvent.Error -> Toast.makeText(
                context,
                event.error.asString(context),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    PokemonFavoritesScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is PokemonFavoritesAction.OnPokemonDetail -> onPokemonDetail(action.id)
                PokemonFavoritesAction.OnBack -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PokemonFavoritesScreen(
    state: PokemonFavoritesViewState,
    onAction: (PokemonFavoritesAction) -> Unit
) {
    PokemonAppScaffold(
        topAppBar = {
            PokedexTopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                config = PokedexTopAppBarConfig(
                    title = stringResource(R.string.favorites),
                    titleColor = PokemonColors.BlueDark,
                    navigationIcon = PokedexTopAppBar.NavigationIcon.Up {
                        onAction(PokemonFavoritesAction.OnBack)
                    }
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    PokemonAppLoading(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.pokemonList.isEmpty() -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = PokemonAppIcons.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorites),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = stringResource(R.string.favorites_empty),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                else -> {
                    PokemonList(
                        modifier = Modifier.padding(16.dp),
                        pokemonList = state.pokemonList,
                        onPokemonClick = { onAction(PokemonFavoritesAction.OnPokemonDetail(it)) },
                        onFavoriteClick = { onAction(PokemonFavoritesAction.OnToggleFavorite(it)) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonFavoritesScreenPreview() {
    PokemonAppTheme {
        PokemonFavoritesScreen(
            state = PokemonFavoritesViewState()
        ) { }
    }
}
