package com.toktasoft.animatedconnectionobserver.observer

import android.app.Application
import android.content.Context
import android.content.ContextWrapper

internal object ConnectionObserverFactory {
    fun create(
        context: Context,
    ): ConnectionObserver {
        val appContext = context.applicationContext ?: context
        val application = (appContext as? Application) ?: (appContext as? ContextWrapper)?.baseContext
        return ConnectionObserverImpl(application ?: appContext)
    }
}
