package com.toktasoft.animatedconnectionobserver.common.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.net.toUri

fun Context.openInBrowser(
    url: String?,
): Result<Unit> =
    runCatching {
        startActivity(Intent(
            Intent.ACTION_VIEW, url.orEmpty().toUri()
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
}

fun Context.openWifiSettings() {
    runCatching {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent(Settings.Panel.ACTION_WIFI)
        } else {
            Intent(Settings.ACTION_WIFI_SETTINGS)
        }
        startActivity(intent)
    }.recoverCatching { throwable ->
        when (throwable) {
            is ActivityNotFoundException -> Intent(Settings.ACTION_SETTINGS).also { startActivity(it) }
            else -> throw throwable
        }
    }.onFailure {
        Toast.makeText(this, "Unable to open settings", Toast.LENGTH_SHORT).show()
    }
}
