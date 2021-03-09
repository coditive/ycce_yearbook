package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.DialogAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.action.ToastNotificationAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.StackRelayFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
class RouteStore constructor(
    private val dispatcher: Dispatcher,
    private val accountStore: AccountStore,
    coroutineContext: CoroutineContext
) {

    private val coroutineScope = CoroutineScope(coroutineContext)
    private val _routes = StackRelayFlow.create<RouteAction>(RouteAction.StartUp)
    open val routes: StateFlow<RouteAction> = _routes.stackStateFlow

    init {
        coroutineScope.launch {
            Timber.d("RouteStore Coroutine Context State : ${this.isActive}")
            launch {
                dispatcher.getDispatcherChannelSubscription()
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
                                is RouteAction.Welcome,
                                is RouteAction.DialogFragment,
                                is RouteAction.SystemIntent,
                                is ToastNotificationAction -> true
                                else -> false
                            }
                        }
                    }
            }

            launch {
                accountStore.accountState.collect {
                    accountStoreToRouteActions(it)
                }
            }
        }
    }

    private fun accountStoreToRouteActions(accountState: AccountStore.State) {
        when(accountState) {
            is AccountStore.State.Welcome -> {
                Timber.d("State Changed to Login, so dispatching RouteAction Welcome")
                dispatcher.dispatch(RouteAction.Welcome)
            }
            is AccountStore.State.LoggedInUserDetails -> {
                Timber.d("Emitting Route Home")
                dispatcher.dispatch(RouteAction.Home)
            }
            is AccountStore.State.Reset -> null
            is AccountStore.State.GoogleLogin -> null
        }
    }

    fun clearBackStack() {
        _routes.trimTail()
    }
}