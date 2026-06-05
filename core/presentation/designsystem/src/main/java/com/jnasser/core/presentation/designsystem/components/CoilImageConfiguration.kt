package com.jnasser.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Stable
data class CoilImageConfiguration(
    val image: String,
    @StringRes val contentDescription: Int? = null,
    val contentScale: ContentScale = ContentScale.Fit,
    val cornerRadius: Int = 0,
    val loadingContent: @Composable (() -> Unit)? = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PokemonAppLoading()
        }
    },
    val errorContent: @Composable (() -> Unit)? = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

        }
    }
)