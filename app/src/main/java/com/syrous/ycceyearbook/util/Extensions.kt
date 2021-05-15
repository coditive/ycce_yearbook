package com.syrous.ycceyearbook.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Recent
import com.syrous.ycceyearbook.model.Resource


@RequiresApi(Build.VERSION_CODES.M)
fun ConnectivityManager.isOnline(network: Network): Boolean {
    return getNetworkCapabilities(network)?.let {
        it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }?: false
}

fun Paper.toRecent(): Recent = Recent(id, "paper", exam, 1)

fun Resource.toRecent(): Recent = Recent(id, "resource", contentType, 1)