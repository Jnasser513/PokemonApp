package com.jnasser.core.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.jnasser.core.presentation.designsystem.R

object PokemonAppIcons {

    val Search: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_search)

    val PokeBall: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_pokeball)

    val ArrowLeft: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_arrow_left)

    val Scale: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_scale)

    val Ruler: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_ruler)

    val Favorite: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_favorite)

    val FavoriteBorder: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_favorite_border)
}
