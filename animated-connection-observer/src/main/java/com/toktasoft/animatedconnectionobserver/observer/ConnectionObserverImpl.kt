package com.toktasoft.animatedconnectionobserver.observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class ConnectionObserverImpl(
    context: Context,
) : ConnectionObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isConnected: Flow<Boolean>
        get() = callbackFlow {
            val cb = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    trySend(false)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    caps: NetworkCapabilities
                ) {
                    val validated = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    trySend(validated)
                }

                override fun onUnavailable() {
                    trySend(false)
                }
            }
            connectivityManager.registerDefaultNetworkCallback(cb)
            awaitClose { connectivityManager.unregisterNetworkCallback(cb) }
        }
}
