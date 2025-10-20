package com.toktasoft.animatedconnectionobserver.observer

import kotlinx.coroutines.flow.Flow

internal interface ConnectionObserver {
    val isConnected: Flow<Boolean>
}