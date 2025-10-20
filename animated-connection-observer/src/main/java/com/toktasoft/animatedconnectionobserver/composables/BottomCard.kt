package com.toktasoft.animatedconnectionobserver.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun BottomCard(
    titleText: String,
    messageText: String,
    cardBackgroundColor: Color,
    cardContentColor: Color,
    textColor: Color,
    navigationButton: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(bottom = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor,
            contentColor = cardContentColor,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Top),
                imageVector = Icons.Default.SignalWifiConnectedNoInternet4,
                tint = textColor,
                contentDescription = null,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            ) {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                )
                if (messageText.isNotBlank()) {
                    Text(
                        text = messageText,
                        color = textColor,
                    )
                }
                navigationButton()
            }
        }
    }
}
