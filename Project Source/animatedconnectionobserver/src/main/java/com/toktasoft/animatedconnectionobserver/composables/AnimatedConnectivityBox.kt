package com.toktasoft.animatedconnectionobserver.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.R
import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy
import com.toktasoft.animatedconnectionobserver.common.slideInFromBottom
import com.toktasoft.animatedconnectionobserver.common.slideOutToBottom
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AnimatedConnectivityBox(
    modifier: Modifier = Modifier,
    isConnected: Boolean,                                  // must
    // blur
    blurWhenOffline: Boolean = true,
    blurRadius: Dp = 14.dp,
    // overlay
    overlayWhenOffline: Boolean = true,
    overlayAlphaWhenOffline: Float = 0.1f,
    // top
    messageBarMessageOffline: String = "No Connection!",
    messageBarMessageBackOnline: String = "Back Online",
    messageBarDelay: Long = 1_000L,
    messageBarSuccessColor: Color = Color(76, 175, 80),
    messageBarErrorColor: Color = Color(234, 27, 55),
    barTextColor: Color = Color.White,
    // bottom
    cardTitleOffline: String = "No Internet Connection!",
    cardMessageOffline: String = "Please Back Online",
    cardDelay: Long = 2000L,
    cardContentColor: Color = Color(225, 217, 217),
    cardBackgroundColor: Color = Color(49, 26, 26),
    cardTextColor: Color = Color.White,
    dampingRatioBouncy: DampingRatioBouncy = DampingRatioBouncy.MEDIUM_HIGH,
    // -----
    navigationButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    var cardVisible by rememberSaveable { mutableStateOf(false) }
    var messageBarVisible by rememberSaveable { mutableStateOf(false) }

    val backgroundColor by animateMessageBarColor(
        isConnected = isConnected,
        messageBarBackgroundColorSuccess = messageBarSuccessColor,
        messageBarBackgroundColorError = messageBarErrorColor,
    )

    val topMessageBarImageVector = ImageVector.vectorResource(
        if (isConnected) R.drawable.animated_connection_observer_ic_check
        else R.drawable.animated_connection_observer_ic_warning
    )
    val messageBarMessage = if (isConnected) messageBarMessageBackOnline else messageBarMessageOffline

    val density = LocalDensity.current
    var messageBarHeightDp by remember { mutableStateOf(0.dp) }

    LaunchedEffect(isConnected) {
        when (isConnected) {
            true -> {
                delay(messageBarDelay.milliseconds)
                cardVisible = false
                delay(cardDelay.milliseconds)
                messageBarVisible = false
            }
            false -> {
                delay(messageBarDelay.milliseconds)
                messageBarVisible = true
                delay(cardDelay.milliseconds)
                cardVisible = true
            }
        }
    }

    Box(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = if (messageBarVisible) messageBarHeightDp else 0.dp)
                .then(
                    if (!isConnected && blurWhenOffline)
                        Modifier.blur(
                            radius = blurRadius,
                        )
                    else Modifier
                ),
        ) {
            content()
        }

        AnimatedVisibility(
            visible = messageBarVisible,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onSizeChanged { size ->
                    messageBarHeightDp = with(density) { size.height.toDp() }
                },
        ) {
            TopMessageBar(
                message = messageBarMessage,
                icon = topMessageBarImageVector,
                backgroundColor = backgroundColor,
                textColor = barTextColor,
            )
        }

        if (!isConnected && overlayWhenOffline) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = overlayAlphaWhenOffline))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                    ),
            )
        }

        AnimatedVisibility(
            visible = cardVisible,
            enter = slideInFromBottom(dampingRatioBouncy.float),
            exit = slideOutToBottom(),
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            BottomCard(
                titleText = cardTitleOffline,
                messageText = cardMessageOffline,
                cardBackgroundColor = cardBackgroundColor,
                cardContentColor = cardContentColor,
                textColor = cardTextColor,
                navigationButton = navigationButton,
            )
        }
    }
}
