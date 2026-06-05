package com.jnasser.core.presentation.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    configuration: CoilImageConfiguration
) {
    SubcomposeAsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = configuration.image,
        contentDescription = configuration.contentDescription?.let { stringResource(it) },
        contentScale = configuration.contentScale,
        loading = { configuration.loadingContent?.invoke() },
        error = { error ->
            error.result.throwable.printStackTrace()
            configuration.errorContent?.invoke()
        }
    )
}