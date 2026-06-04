package com.jnasser.core.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jnasser.core.domain.util.error_handler.DataError

@Composable
fun DataErrorText(error: DataError) {
    Text(text = error.toString())
}
