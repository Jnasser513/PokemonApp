package com.jnasser.core.presentation.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnasser.core.presentation.designsystem.theme.PokemonAppTheme

@Composable
fun PokemonAppLoading(
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.primary
) {
    CircularProgressIndicator(
        modifier = modifier
            .size(40.dp),
        color = trackColor,
        strokeWidth = 3.dp
    )
}

@Preview
@Composable
private fun PokemonAppLoadingPreview() {
    PokemonAppTheme {
        PokemonAppLoading(
            trackColor = MaterialTheme.colorScheme.primary
        )
    }
}