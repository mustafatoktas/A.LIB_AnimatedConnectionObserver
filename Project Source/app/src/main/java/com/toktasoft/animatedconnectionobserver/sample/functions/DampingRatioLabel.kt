package com.toktasoft.animatedconnectionobserver.sample.functions

import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy

internal fun dampingRatioLabel(ratio: DampingRatioBouncy): String =
    when (ratio) {
        DampingRatioBouncy.HIGH -> "Çok hareketli"
        DampingRatioBouncy.MEDIUM_HIGH -> "Hareketli"
        DampingRatioBouncy.MEDIUM -> "Dengeli"
        DampingRatioBouncy.LOW -> "Yumuşak"
    }