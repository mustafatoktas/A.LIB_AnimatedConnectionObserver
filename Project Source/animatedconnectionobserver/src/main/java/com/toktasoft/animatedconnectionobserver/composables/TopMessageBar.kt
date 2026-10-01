package com.toktasoft.animatedconnectionobserver.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
internal fun TopMessageBar(
    message: String,
    icon: ImageVector,
    backgroundColor: Color,
    textColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .statusBarsPadding()
            .padding(5.dp)
            .padding(start = 10.dp),
    ) {
        Icon(
            imageVector = icon,
            tint = textColor,
            contentDescription = null,
        )
        Text(
            text = message,
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
