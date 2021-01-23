package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.DialogAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.action.ToastNotificationAction
import com.syrous.ycceyearbook.flux.Action
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.StackRelayFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
class RouteStore @Inject constructor(
    private val dispatcher: Dispatcher,
    private val coroutineContext: CoroutineContext
) {

    private val coroutineScope = CoroutineScope(coroutineContext)
    private val _routes = StackRelayFlow.create<RouteAction>(RouteAction.StartUp)
    open val routes: StateFlow<RouteAction> = _routes.stackStateFlow

    fun observeRouteAction() {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<RouteAction>()
                .collect {
                        routeAction ->
                    when(routeAction) {
                        is RouteAction.InternalBack -> _routes.safePop()
                        else -> _routes.addToStack(routeAction)
                    }

                    _routes.trim {
                        when (it) {
                            is DialogAction,
                            is RouteAction.DialogFragment,
                            is RouteAction.SystemIntent,
                            is ToastNotificationAction -> true
                            else -> false
                        }
                    }
                }
        }
    }

    init {
        observeRouteAction()
    }

    fun clearBackStack() {
        _routes.trimTail()
    }
}