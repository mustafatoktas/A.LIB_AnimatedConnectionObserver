package com.toktasoft.animatedconnectionobserver.sample.model

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy

internal data class PreviewSettings(
    val blurWhenOffline: Boolean = true,
    val blurRadius: Int = 14,              // dp
    val overlayWhenOffline: Boolean = true,
    val overlayPercent: Int = 10,          // overlay alpha × 100
    val dampingRatio: DampingRatioBouncy = DampingRatioBouncy.MEDIUM_HIGH,
) {
    val isDefault: Boolean get() = this == PreviewSettings()

    companion object {
        val Saver: Saver<PreviewSettings, Any> = listSaver(
            save = {
                listOf(
                    it.blurWhenOffline,
                    it.blurRadius,
                    it.overlayWhenOffline,
                    it.overlayPercent,
                    it.dampingRatio.name,
                )
            },
            restore = {
                PreviewSettings(
                    blurWhenOffline = it[0] as Boolean,
                    blurRadius = it[1] as Int,
                    overlayWhenOffline = it[2] as Boolean,
                    overlayPercent = it[3] as Int,
                    dampingRatio = DampingRatioBouncy.valueOf(it[4] as String),
                )
            },
        )
    }
}
