package com.toktasoft.animatedconnectionobserver.sample.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy
import com.toktasoft.animatedconnectionobserver.sample.R
import com.toktasoft.animatedconnectionobserver.sample.functions.dampingRatioLabel
import com.toktasoft.animatedconnectionobserver.sample.functions.percentLabel
import com.toktasoft.animatedconnectionobserver.sample.model.PreviewSettings
import kotlin.math.roundToInt

@Composable
internal fun SettingsCard(
    settings: PreviewSettings,
    onSettingsChange: (PreviewSettings) -> Unit,
    onReset: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // No horizontal padding on the card: sections pad themselves so switch ripples span the full width.
        Column(
            modifier = Modifier.padding(vertical = 6.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .padding(start = 12.dp, end = 4.dp),
            ) {
                Text(
                    text = stringResource(R.string.settings_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                // Only offered when something differs from the defaults.
                AnimatedVisibility(
                    visible = !settings.isDefault,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    TextButton(
                        onClick = onReset,
                    ) {
                        Text(stringResource(R.string.settings_reset))
                    }
                }
            }

            SwitchRow(
                title = stringResource(R.string.settings_blur),
                checked = settings.blurWhenOffline,
                onCheckedChange = { onSettingsChange(settings.copy(blurWhenOffline = it)) },
            )
            AnimatedVisibility(
                visible = settings.blurWhenOffline,
            ) {
                SliderRow(
                    title = stringResource(R.string.settings_blur_radius),
                    valueLabel = stringResource(R.string.dp_value, settings.blurRadius),
                    value = settings.blurRadius.toFloat(),
                    valueRange = 0f..32f,
                    onValueChange = { onSettingsChange(settings.copy(blurRadius = it.roundToInt())) },
                )
            }
            RowDivider()

            SwitchRow(
                title = stringResource(R.string.settings_overlay),
                checked = settings.overlayWhenOffline,
                onCheckedChange = { onSettingsChange(settings.copy(overlayWhenOffline = it)) },
            )
            AnimatedVisibility(
                visible = settings.overlayWhenOffline,
            ) {
                SliderRow(
                    title = stringResource(R.string.settings_overlay_alpha),
                    valueLabel = percentLabel(settings.overlayPercent),
                    value = settings.overlayPercent.toFloat(),
                    valueRange = 0f..40f,
                    onValueChange = { onSettingsChange(settings.copy(overlayPercent = it.roundToInt())) },
                )
            }
            RowDivider()

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 6.dp),
            ) {
                Text(
                    text = stringResource(R.string.settings_card_animation),
                    style = MaterialTheme.typography.labelLarge,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                ) {
                    DampingRatioBouncy.entries.forEach { ratio ->
                        FilterChip(
                            selected = ratio == settings.dampingRatio,
                            onClick = { onSettingsChange(settings.copy(dampingRatio = ratio)) },
                            label = { Text(dampingRatioLabel(ratio)) },
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.settings_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        modifier = Modifier.padding(horizontal = 12.dp),
    )
}

@Composable
private fun SwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    // The whole row toggles the switch; padding comes after toggleable so the ripple fills the row.
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                role = Role.Switch,
                onValueChange = onCheckedChange,
            )
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .heightIn(min = 48.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = checked,
            onCheckedChange = null,
        )
    }
}

@Composable
private fun SliderRow(
    title: String,
    valueLabel: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 6.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = valueLabel,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Slider(
            value = value,
            valueRange = valueRange,
            onValueChange = onValueChange,
        )
    }
}
