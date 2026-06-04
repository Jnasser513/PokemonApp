package com.jnasser.core.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed interface UiText {
    @Composable
    fun asString(): String

    data class DynamicString(val value: String) : UiText {
        @Composable
        override fun asString(): String = value
    }

    data class StringResource(
        @StringRes val id: Int,
        val args: List<Any> = emptyList(),
    ) : UiText {
        @Composable
        override fun asString(): String = LocalContext.current.getString(id, *args.toTypedArray())
    }
}
