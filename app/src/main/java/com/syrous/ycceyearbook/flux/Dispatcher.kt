package com.syrous.ycceyearbook.flux

import com.syrous.ycceyearbook.action.RouteAction
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class Dispatcher (override val coroutineContext: CoroutineContext): CoroutineScope {
    private val actionChannel = BroadcastChannel<Action>(1)

    fun dispatch(action: Action) = launch {
            actionChannel.send(action)
    }

    fun getDispatcherChannelSubscription(): ReceiveChannel<Action> =
        actionChannel.openSubscription()

    fun closeDispatcherChannel() {
        actionChannel.close()
    }
}