package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.LifecycleAction
import com.syrous.ycceyearbook.flux.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class LifecycleStore (
    private val dispatcher: Dispatcher
){
    suspend fun getLifecycleEvents(coroutineScope: CoroutineScope) : StateFlow<LifecycleAction> =
        dispatcher.getDispatcherChannelSubscription()
            .receiveAsFlow().filterIsInstance<LifecycleAction>().stateIn(coroutineScope)

}