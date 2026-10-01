package com.toktasoft.animatedconnectionobserver.sample.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy
import com.toktasoft.animatedconnectionobserver.sample.functions.dampingRatioLabel
import kotlin.math.roundToInt

@Composable
internal fun SettingsCard(
    blurWhenOffline: Boolean,
    onBlurWhenOfflineChange: (Boolean) -> Unit,
    overlayWhenOffline: Boolean,
    onOverlayWhenOfflineChange: (Boolean) -> Unit,
    useTurkishMessages: Boolean,
    onUseTurkishMessagesChange: (Boolean) -> Unit,
    blurRadius: Float,
    onBlurRadiusChange: (Float) -> Unit,
    overlayAlpha: Float,
    onOverlayAlphaChange: (Float) -> Unit,
    dampingRatio: DampingRatioBouncy,
    onDampingRatioChange: (DampingRatioBouncy) -> Unit,
    onReset: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Bileşen ayarları",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                TextButton(onClick = onReset) {
                    Text("Sıfırla")
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                SettingSwitch(
                    title = "Çevrimdışıyken bulanıklaştır",
                    checked = blurWhenOffline,
                    onCheckedChange = onBlurWhenOfflineChange,
                )
                AnimatedVisibility(
                    visible = blurWhenOffline,
                ) {
                    SettingSlider(
                        title = "Bulanıklık yarıçapı",
                        valueLabel = "${blurRadius.roundToInt()} dp",
                        value = blurRadius,
                        onValueChange = onBlurRadiusChange,
                        valueRange = 0f..32f,
                    )
                }
            }

            HorizontalDivider()

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                SettingSwitch(
                    title = "Çevrimdışıyken etkileşimi engelle",
                    checked = overlayWhenOffline,
                    onCheckedChange = onOverlayWhenOfflineChange,
                )
                AnimatedVisibility(
                    visible = overlayWhenOffline,
                ) {
                    SettingSlider(
                        title = "Engel katmanı opaklığı",
                        valueLabel = "${(overlayAlpha * 100).roundToInt()}%",
                        value = overlayAlpha,
                        onValueChange = onOverlayAlphaChange,
                        valueRange = 0f..0.40f,
                    )
                }
            }

            HorizontalDivider()

            SettingSwitch(
                title = "Türkçe mesajlar",
                checked = useTurkishMessages,
                onCheckedChange = onUseTurkishMessagesChange,
            )

            HorizontalDivider()

            Text(
                text = "Kart animasyonu",
                style = MaterialTheme.typography.labelLarge,
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                DampingRatioBouncy.entries.forEach { ratio ->
                    FilterChip(
                        selected = ratio == dampingRatio,
                        onClick = { onDampingRatioChange(ratio) },
                        label = { Text(dampingRatioLabel(ratio)) },
                    )
                }
            }
            Text(
                text = "Ayarları değiştirirken önizleme anında güncellenir.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
