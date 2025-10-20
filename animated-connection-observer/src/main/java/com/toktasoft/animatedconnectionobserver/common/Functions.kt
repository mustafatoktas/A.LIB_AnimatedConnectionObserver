package com.toktasoft.animatedconnectionobserver.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

internal fun currentConnectivity(
    context: Context,
): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val active = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(active) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}