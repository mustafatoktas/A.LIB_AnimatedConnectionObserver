package com.toktasoft.animatedconnectionobserver.sample.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.sample.functions.connectionLabel

@Composable
internal fun ConnectionSourceCard(
    useRealConnection: Boolean,
    realConnection: Boolean,
    simulatedConnection: Boolean,
    effectiveConnection: Boolean,
    onUseRealConnectionChange: (Boolean) -> Unit,
    onSimulatedConnectionChange: (Boolean) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Bağlantı kaynağı",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Cihaz: ${connectionLabel(realConnection)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                ConnectionBadge(isConnected = effectiveConnection)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    selected = useRealConnection,
                    onClick = { onUseRealConnectionChange(true) },
                    label = { Text("Gerçek ağ") },
                )
                FilterChip(
                    selected = !useRealConnection,
                    onClick = { onUseRealConnectionChange(false) },
                    label = { Text("Simülasyon") },
                )
            }

            AnimatedVisibility(
                visible = !useRealConnection,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    FilterChip(
                        selected = simulatedConnection,
                        onClick = { onSimulatedConnectionChange(true) },
                        label = { Text("Çevrimiçi") },
                    )
                    FilterChip(
                        selected = !simulatedConnection,
                        onClick = { onSimulatedConnectionChange(false) },
                        label = { Text("Çevrimdışı") },
                    )
                }
            }
        }
    }
}


