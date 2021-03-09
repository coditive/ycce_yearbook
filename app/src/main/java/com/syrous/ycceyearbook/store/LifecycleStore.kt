package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.LifecycleAction
import com.syrous.ycceyearbook.flux.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.stateIn

class LifecycleStore (
    private val dispatcher: Dispatcher
){
    suspend fun getLifecycleEvents(coroutineScope: CoroutineScope) : StateFlow<LifecycleAction> =
        dispatcher.getDispatcherChannelSubscription()
            .filterIsInstance<LifecycleAction>().stateIn(coroutineScope)

}