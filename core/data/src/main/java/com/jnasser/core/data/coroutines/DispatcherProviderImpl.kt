package com.jnasser.core.data.coroutines

import com.jnasser.core.domain.coroutines.DispatcherProvider
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
}
