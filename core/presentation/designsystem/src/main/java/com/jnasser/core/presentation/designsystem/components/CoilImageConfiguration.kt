package com.jnasser.core.presentation.designsystem.components

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale

data class CoilImageConfiguration(
    val contentScale: ContentScale = ContentScale.Crop,
    val alignment: Alignment = Alignment.Center,
)
