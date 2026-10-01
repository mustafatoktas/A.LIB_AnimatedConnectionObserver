package com.toktasoft.animatedconnectionobserver.sample.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.sample.functions.connectionLabel

@Composable
internal fun ConnectionBadge(
    isConnected: Boolean,
) {
    val color = if (isConnected) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error

    Surface(
        color = color.copy(alpha = 0.12f),
        shape = RoundedCornerShape(100.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape),
            )
            Text(
                text = connectionLabel(isConnected),
                color = color,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}