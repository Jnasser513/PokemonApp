package com.jnasser.core.presentation.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun CoilImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    configuration: CoilImageConfiguration = CoilImageConfiguration(),
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = configuration.contentScale,
        alignment = configuration.alignment,
    )
}
