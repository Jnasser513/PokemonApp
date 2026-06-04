package com.jnasser.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes

/**
 * Represents text that can be displayed in the UI.
 * It can be either a dynamic string or a localized string resource.
 */
sealed interface UiText {

    /**
     * Represents a dynamic string that is created at runtime.
     */
    data class DynamicString(val value: String): UiText

    /**
     * Represents a string resource ID with optional formatting arguments.
     */
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    /**
     * Converts the UiText into a String using the provided Context.
     */
    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}