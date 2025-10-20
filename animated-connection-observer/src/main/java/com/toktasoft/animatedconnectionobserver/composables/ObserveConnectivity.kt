package com.toktasoft.animatedconnectionobserver.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.toktasoft.animatedconnectionobserver.common.currentConnectivity
import com.toktasoft.animatedconnectionobserver.observer.ConnectionObserverFactory
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.runtime.State as ComposeState

@Composable
fun observeConnectivity(
    context: Context = LocalContext.current,
): ComposeState<Boolean> {
    val observer = remember(context) { ConnectionObserverFactory.create(context) }

    val initialValue = remember(context) { currentConnectivity(context) }

    val flow = remember(observer) {
        observer.isConnected.distinctUntilChanged()
    }

    return flow.collectAsStateWithLifecycle(initialValue = initialValue)
}
