package com.syrous.ycceyearbook.flux

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class Dispatcher (override val coroutineContext: CoroutineContext): CoroutineScope {
    private val actionChannel = MutableSharedFlow<Action>(1)

    fun dispatch(action: Action) = launch {
        actionChannel.emit(action)
    }

    fun getDispatcherChannelSubscription(): SharedFlow<Action> =
        actionChannel

}