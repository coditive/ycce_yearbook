package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.flux.Dispatcher
import io.sentry.Sentry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
class SentryStore(
    dispatcher: Dispatcher,
    coroutineContext: CoroutineContext
) {

    private val coroutineScope = CoroutineScope(coroutineContext)

    init {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<SentryAction>()
                .map { it.error }
                .collect {
                    Sentry.captureException(it)
                }
        }
    }
}