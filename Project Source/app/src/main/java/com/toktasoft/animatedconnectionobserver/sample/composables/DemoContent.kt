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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.sample.R
import com.toktasoft.animatedconnectionobserver.sample.functions.percentLabel

@Composable
internal fun DemoContent(
    clickCount: Int,
    onButtonClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Text(
            text = stringResource(R.string.demo_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Button(
            onClick = onButtonClick,
        ) {
            Text(text = stringResource(R.string.demo_button, clickCount))
        }
        Text(
            text = stringResource(R.string.demo_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            DemoMetric(
                modifier = Modifier.weight(1f),
                value = "24",
                label = stringResource(R.string.demo_pending),
            )
            DemoMetric(
                modifier = Modifier.weight(1f),
                value = percentLabel(98),
                label = stringResource(R.string.demo_sync),
            )
        }
    }
}
