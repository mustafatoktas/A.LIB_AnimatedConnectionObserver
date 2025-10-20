package com.toktasoft.animatedconnectionobserver.presentation.main.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.R
import com.toktasoft.animatedconnectionobserver.common.constants.Label
import com.toktasoft.animatedconnectionobserver.presentation.ui.connectionColor
import com.toktasoft.animatedconnectionobserver.presentation.ui.disconnectionColor

@Composable
fun WifiCard(
    isConnected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(top = 8.dp),
        onClick = onClick,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 10.dp).fillMaxWidth(),
        ) {
            Crossfade(
                targetState = isConnected,
                label = Label.CROSS_FADE_IMAGE_LABEL,
                animationSpec = tween(durationMillis = 1_000),
            ) { connected ->
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = if (connected) painterResource(R.drawable.yes_internet) else painterResource(R.drawable.no_internet),
                    contentDescription = null,
                )
            }
            Text(
                text = if (isConnected) "Connected" else "Disconnected",
                style = MaterialTheme.typography.titleLarge,
                color = if (isConnected) connectionColor else disconnectionColor,
            )
        }
    }
}
