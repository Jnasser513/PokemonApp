package com.jnasser.core.presentation.designsystem.extensions

import androidx.compose.ui.graphics.Color

fun Color.toPastel(factor: Float = 0.8f): Color {
    return Color(
        red = red + (1f - red) * factor,
        green = green + (1f - green) * factor,
        blue = blue + (1f - blue) * factor,
        alpha = alpha
    )
}
