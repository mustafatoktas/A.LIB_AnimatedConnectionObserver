package com.toktasoft.animatedconnectionobserver.sample.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun VerticalScrollbar(
    state: LazyListState,
    modifier: Modifier = Modifier,
    thumbColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.72f),
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
) {
    val indicatorState = state.scrollIndicatorState ?: return

    Canvas(modifier = modifier) {
        val viewportSize = indicatorState.viewportSize
        val contentSize = indicatorState.contentSize
        val scrollOffset = indicatorState.scrollOffset

        if (
            viewportSize <= 0 ||
            contentSize <= viewportSize ||
            viewportSize == Int.MAX_VALUE ||
            contentSize == Int.MAX_VALUE ||
            scrollOffset == Int.MAX_VALUE
        ) {
            return@Canvas
        }

        val trackHeight = size.height
        val minimumThumbHeight = 36.dp.toPx().coerceAtMost(trackHeight)
        val thumbHeight = (trackHeight * viewportSize / contentSize)
            .coerceIn(minimumThumbHeight, trackHeight)
        val scrollRange = (contentSize - viewportSize).toFloat()
        val thumbRange = trackHeight - thumbHeight
        val thumbOffset = thumbRange * (scrollOffset / scrollRange).coerceIn(0f, 1f)
        val cornerRadius = CornerRadius(size.width / 2f)

        drawRoundRect(
            color = trackColor,
            size = Size(size.width, trackHeight),
            cornerRadius = cornerRadius,
        )
        drawRoundRect(
            color = thumbColor,
            topLeft = Offset(x = 0f, y = thumbOffset),
            size = Size(width = size.width, height = thumbHeight),
            cornerRadius = cornerRadius,
        )
    }
}
