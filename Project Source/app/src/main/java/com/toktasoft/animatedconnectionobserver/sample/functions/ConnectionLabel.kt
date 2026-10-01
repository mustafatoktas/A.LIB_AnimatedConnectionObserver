package com.toktasoft.animatedconnectionobserver.sample.functions

internal fun connectionLabel(isConnected: Boolean): String =
    if (isConnected) "Çevrimiçi" else "Çevrimdışı"