@file:OptIn(ExperimentalMaterial3Api::class)

package com.jnasser.pokemon.presentation.pokemon_detail.composables

import PokemonAppScaffold
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.domain.pokemon.model.PokemonType
import com.jnasser.core.presentation.designsystem.components.PokedexTopAppBar
import com.jnasser.core.presentation.designsystem.components.PokedexTopAppBarConfig
import com.jnasser.core.presentation.designsystem.enums.PokemonTypeEnum
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.core.presentation.designsystem.theme.PokemonColors
import com.jnasser.core.presentation.ui.ObserveAsEvents
import com.jnasser.pokemon.presentation.R
import com.jnasser.pokemon.presentation.pokemon_detail.PokemonDetailAction
import com.jnasser.pokemon.presentation.pokemon_detail.PokemonDetailEvent
import com.jnasser.pokemon.presentation.pokemon_detail.PokemonDetailViewModel
import com.jnasser.pokemon.presentation.pokemon_detail.PokemonDetailViewState
import com.jnasser.pokemon.presentation.pokemon_detail.model.ImageContainerDataUi
import com.jnasser.pokemon.presentation.pokemon_detail.model.MeasuresContainerDataUi
import com.jnasser.pokemon.presentation.pokemon_detail.model.PokemonDetailDataUi
import com.jnasser.pokemon.presentation.pokemon_detail.mappers.toPokemonStatDataUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailScreenRoot(
    viewModel: PokemonDetailViewModel = koinViewModel(),
    pokemonId: Int,
    onReturn: () -> Unit
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is PokemonDetailEvent.Error -> Toast.makeText(
                context,
                event.error.asString(context),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(pokemonId) {
        viewModel.getPokemonById(pokemonId)
    }

    PokemonDetailScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                PokemonDetailAction.OnReturn -> onReturn()
            }
        }
    )
}

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailViewState,
    onAction: (PokemonDetailAction) -> Unit
) {
    PokemonAppScaffold(
        topAppBar = {
            PokedexTopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                config = PokedexTopAppBarConfig(
                    title = state.pokemonDetail.name,
                    centerTitle = false,
                    navigationIcon = PokedexTopAppBar.NavigationIcon.Up {
                        onAction(PokemonDetailAction.OnReturn)
                    },
                    actions = listOf(
                        PokedexTopAppBar.Action(
                            text = stringResource(R.string.pokemon_number, state.pokemonDetail.number.padStart(3, '0')),
                            tintColor = PokemonColors.Gray300
                        )
                    )
                )
            )
        }
    ) { padding ->

        val pokemonType = PokemonTypeEnum.fromName(
            state.pokemonDetail.types.getOrNull(0)?.type.toString()
        )

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                ImageContainer(
                    imageContainerDataUi = ImageContainerDataUi(
                        image = state.pokemonDetail.image,
                        types = state.pokemonDetail.types
                    )
                )
            }

            item {
                MeasuresContainer(
                    modifier = Modifier.padding(top = 10.dp),
                    measuresContainerDataUi = MeasuresContainerDataUi(
                        height = state.pokemonDetail.height,
                        weight = state.pokemonDetail.weight
                    )
                )
            }

            item {
                state.pokemonDetail.description?.let {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 30.dp)
                            .fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = PokemonColors.Gray300
                    )
                }
            }

            item {
                StatsContainer(
                    statContainerDataUi = state.pokemonDetail.stats.map { it.toPokemonStatDataUi() },
                    color = pokemonType.color
                )
            }
        }
    }
}

@Preview
@Composable
private fun PokemonDetailScreenPreview() {
    PokemonAppTheme {
        PokemonDetailScreen(
            state = PokemonDetailViewState(
                pokemonDetail = PokemonDetailDataUi(
                    name = "Charizard",
                    number = "6",
                    description = "La figura de Charizard es la de un dragón erguido sobre sus dos patas traseras, que sostienen su peso. Posee unas poderosas alas y un abrasador aliento de fuego.",
                    types = listOf(
                        PokemonType(
                            pokemonId = 1,
                            type = "Fire"
                        )
                    )
                )
            )
        ) { }
    }
}