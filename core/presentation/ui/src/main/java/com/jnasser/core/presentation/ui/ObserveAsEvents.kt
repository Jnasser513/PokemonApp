package com.jnasser.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Collects a flow of one-time events and handles them when the lifecycle is at least STARTED.
 *
 * @param flow The flow to observe.
 * @param key1 Optional key to control recomposition of LaunchedEffect.
 * @param key2 Optional second key.
 * @param onEvent Called each time a new event is emitted from the flow.
 */
@Composable
fun<T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    // Launch a coroutine when the flow or lifecycle changes
    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        // Observe the flow only when the lifecycle is at least STARTED
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Ensure events are handled on the main thread
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}
