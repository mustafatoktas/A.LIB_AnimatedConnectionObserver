package com.toktasoft.animatedconnectionobserver.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

private const val DEFAULT_DURATION_MILLIS = 700

internal fun slideInFromBottom(
    dampingRatio: Float,
): EnterTransition =
    slideInVertically(
        initialOffsetY = { it },
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = Spring.StiffnessVeryLow,
        ),
    ) + fadeIn()

internal fun slideOutToBottom(
    durationMillis: Int = DEFAULT_DURATION_MILLIS,
): ExitTransition =
    slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(durationMillis),
    ) + fadeOut()
