package com.syrous.ycceyearbook.presenter

import android.content.Context
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.store.AccountStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface SplashView {
    val coroutineScope: CoroutineScope
}

class SplashPresenter(
    private val view: SplashView,
): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var accountStore: AccountStore

    override fun onViewReady() {
        super.onViewReady()
        view.coroutineScope.launch {
            accountStore.accountState.collect {
                accountStoreToRouteActions(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }

    private suspend fun accountStoreToRouteActions(accountState: AccountStore.State) {
        when(accountState) {
            is AccountStore.State.Welcome -> {
                delay(1500)
                Timber.d("State Changed to Login, so dispatching RouteAction Welcome")
                dispatcher.dispatch(RouteAction.Welcome)
            }
            is AccountStore.State.LoggedInUserDetails -> {
                delay(1500)
                Timber.d("Emitting Route Home")
                dispatcher.dispatch(RouteAction.Home)
            }
            is AccountStore.State.Reset -> null
            is AccountStore.State.GoogleLogin -> null
        }
    }
}