package com.toktasoft.animatedconnectionobserver.common.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.net.toUri

fun Context.openInBrowser(url: String?): Result<Unit> = runCatching {
    startActivity(Intent(
        Intent.ACTION_VIEW, url.orEmpty().toUri()
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

fun Context.openWifiSettings() {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            startActivity(Intent(Settings.Panel.ACTION_WIFI))
        else
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    } catch (_: ActivityNotFoundException) {
        try {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        } catch (_: Exception) {
            Toast.makeText(this, "Unable to open settings", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
    }
}
