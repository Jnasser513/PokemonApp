package com.jnasser.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    onEvent: (T) -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    androidx.compose.runtime.LaunchedEffect(flow, lifecycle) {
        flow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect(onEvent)
    }
}
