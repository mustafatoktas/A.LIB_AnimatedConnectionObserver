package com.toktasoft.animatedconnectionobserver.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.toktasoft.animatedconnectionobserver.common.Label

@Composable
internal fun animateMessageBarColor(
    isConnected: Boolean,
    messageBarBackgroundColorSuccess: Color,
    messageBarBackgroundColorError: Color,
): State<Color> =
    animateColorAsState(
        animationSpec = tween(durationMillis = 500),
        label = Label.ANIMATE_MESSAGE_BAR_COLOR,
        targetValue = if (isConnected) messageBarBackgroundColorSuccess else messageBarBackgroundColorError,
    )