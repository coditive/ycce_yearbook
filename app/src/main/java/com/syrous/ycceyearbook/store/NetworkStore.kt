package com.syrous.ycceyearbook.store

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import com.syrous.ycceyearbook.action.LifecycleAction
import com.syrous.ycceyearbook.action.NetworkAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.util.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalCoroutinesApi
class NetworkStore(
    context: Context,
    dispatcher: Dispatcher,
    coroutineContext: CoroutineContext
) {
    var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val coroutineScope = CoroutineScope(coroutineContext)
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    var isConnectedState: Boolean = false
        internal set

    private val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isConnectedState = connectivityManager.isOnline(network)
        }

        override fun onLost(network: Network) {
            isConnectedState = connectivityManager.isOnline(network)
        }
    }

    init {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<NetworkAction>()
                .collect {
                    when(it) {
                        is NetworkAction.CheckConnectivity -> {
                            checkConnectivity()
                        }
                    }
                }

            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<LifecycleAction>()
                .collect {
                    when(it) {
                        LifecycleAction.Startup -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            connectivityManager.registerDefaultNetworkCallback(connectivityCallback)
                        }
                        LifecycleAction.Background -> connectivityManager.unregisterNetworkCallback(connectivityCallback)

                        LifecycleAction.Foreground -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            connectivityManager.registerDefaultNetworkCallback(connectivityCallback)
                        }
                        LifecycleAction.ShutDown -> connectivityManager.unregisterNetworkCallback(connectivityCallback)
                    }
                }
        }
    }

    private suspend fun checkConnectivity() {
        _isConnected.emit(isConnectedState)
    }
}