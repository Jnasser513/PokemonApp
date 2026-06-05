package com.jnasser.pokemon.presentation.pokemon_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.domain.pokemon.model.PokemonType
import com.jnasser.core.presentation.designsystem.components.CoilImage
import com.jnasser.core.presentation.designsystem.components.CoilImageConfiguration
import com.jnasser.core.presentation.designsystem.components.PokedexChip
import com.jnasser.core.presentation.designsystem.components.PokedexChipConfig
import com.jnasser.core.presentation.designsystem.enums.PokemonTypeEnum
import com.jnasser.core.presentation.designsystem.extensions.toPastel
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme
import com.jnasser.pokemon.presentation.R
import com.jnasser.pokemon.presentation.pokemon_detail.model.ImageContainerDataUi

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    imageContainerDataUi: ImageContainerDataUi
) {
    val pokemonType = PokemonTypeEnum.fromName(
        imageContainerDataUi.types.getOrNull(0)?.type?.lowercase().toString()
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = pokemonType.color.toPastel(0.3f),
                        shape = RoundedCornerShape(15.dp)
                    )
            )
        }
        ImageWithTypes(
            modifier = Modifier.padding(vertical = 15.dp),
            image = imageContainerDataUi.image,
            types = imageContainerDataUi.types
        )
    }
}

@Composable
fun ImageWithTypes(
    modifier: Modifier = Modifier,
    image: String,
    types: List<PokemonType>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            modifier = Modifier
                .height(230.dp),
            configuration = CoilImageConfiguration(
                image = image,
                contentDescription = R.string.pokemon,
            )
        )
        Row {
            types.forEach { type ->
                PokedexChip(
                    config = PokedexChipConfig(
                        type = PokemonTypeEnum.fromName(type.type.lowercase())
                    ) {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageContainerPreview() {
    PokemonAppTheme {
        ImageContainer(
            imageContainerDataUi = ImageContainerDataUi(
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/6.svg",
                types = listOf(
                    PokemonType(
                        1, "Fuego"
                    )
                )
            )
        )
    }
}