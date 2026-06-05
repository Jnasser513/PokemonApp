package com.jnasser.pokemon.presentation.pokemon_list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import com.jnasser.core.presentation.designsystem.components.PokemonAppLoading
import com.jnasser.core.presentation.designsystem.extensions.shadow
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.core.presentation.designsystem.theme.PokemonColors
import com.jnasser.pokemon.presentation.R
import com.jnasser.pokemon.presentation.pokemon_list.model.PokemonListDataUi

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: PokemonListDataUi,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                color = Color(0x1A78B1B5),
                borderRadius = 10.dp,
                offsetX = 0.dp,
                offsetY = 2.dp,
                spread = 0.dp,
                blurRadius = 28.dp
            )
            .clickable { onClick(pokemon.number) },
        shape = RoundedCornerShape(10.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = PokemonColors.Gray200,
            disabledContainerColor = PokemonColors.Gray400
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            val (number, image, name) = createRefs()

            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .constrainAs(number) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                ,
                text = stringResource(R.string.pokemon_number, pokemon.number.padStart(3, '0')),
                color = PokemonColors.Gray200,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.End
            )

            SubcomposeAsyncImage(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 15.dp)
                    .fillMaxWidth()
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(number.bottom)
                        bottom.linkTo(name.top)
                    },
                model = pokemon.imageUrl,
                contentDescription = stringResource(R.string.pokemon_name),
                error = {
                    // TODO("Add error image")
                },
                loading = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        PokemonAppLoading()
                    }
                }
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                text = pokemon.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
private fun PokemonItemPreview() {
    PokemonAppTheme {
        PokemonItem(
            pokemon = PokemonListDataUi(
                name = "Aron",
                number = "304",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
            )
        ) {}
    }
}