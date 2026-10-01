package com.toktasoft.animatedconnectionobserver.sample.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun DemoContent(
    clickCount: Int,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = "Örnek içerik",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Button(
            onClick = onButtonClick,
        ) {
            Text("Örnek buton · $clickCount")
        }
        Text(
            text = "Etkileşim engeli açıkken çevrimdışı durumda bu butona basılamaz.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            DemoMetric(
                modifier = Modifier.weight(1f),
                value = "24",
                label = "Bekleyen işlem",
            )
            DemoMetric(
                modifier = Modifier.weight(1f),
                value = "%98",
                label = "Senkronizasyon",
            )
        }
    }
}
