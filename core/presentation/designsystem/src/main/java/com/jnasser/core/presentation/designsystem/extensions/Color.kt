package com.jnasser.core.presentation.designsystem.extensions

import androidx.compose.ui.graphics.Color

val Color.opacity50: Color
    get() = copy(alpha = 0.5f)
