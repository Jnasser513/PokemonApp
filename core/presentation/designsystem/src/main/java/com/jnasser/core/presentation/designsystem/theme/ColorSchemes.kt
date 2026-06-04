package com.jnasser.core.presentation.designsystem.theme

import androidx.compose.material3.lightColorScheme

val PokemonLightColorScheme = lightColorScheme(
    primary = PokemonColors.BluePrimary,
    onPrimary = PokemonColors.White,
    primaryContainer = PokemonColors.Blue100,
    onPrimaryContainer = PokemonColors.BlueDarker,

    secondary = PokemonColors.PurplePrimary,
    onSecondary = PokemonColors.White,

    background = PokemonColors.White,
    onBackground = PokemonColors.Black,

    surface = PokemonColors.Gray50,
    onSurface = PokemonColors.Gray400,

    error = PokemonColors.RedPrimary,
    onError = PokemonColors.White
)