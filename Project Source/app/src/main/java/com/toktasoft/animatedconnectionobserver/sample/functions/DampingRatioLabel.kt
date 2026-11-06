package com.toktasoft.animatedconnectionobserver.sample.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy
import com.toktasoft.animatedconnectionobserver.sample.R

@Composable
internal fun dampingRatioLabel(ratio: DampingRatioBouncy): String = stringResource(
    when (ratio) {
        DampingRatioBouncy.HIGH -> R.string.damping_high
        DampingRatioBouncy.MEDIUM_HIGH -> R.string.damping_medium_high
        DampingRatioBouncy.MEDIUM -> R.string.damping_medium
        DampingRatioBouncy.LOW -> R.string.damping_low
    }
)
