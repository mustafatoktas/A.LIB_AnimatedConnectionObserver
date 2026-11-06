package com.toktasoft.animatedconnectionobserver.sample

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.composables.AnimatedConnectivityBox
import com.toktasoft.animatedconnectionobserver.composables.observeConnectivity
import com.toktasoft.animatedconnectionobserver.sample.composables.ConnectionSourceCard
import com.toktasoft.animatedconnectionobserver.sample.composables.DemoContent
import com.toktasoft.animatedconnectionobserver.sample.composables.SettingsCard
import com.toktasoft.animatedconnectionobserver.sample.composables.VerticalScrollbar
import com.toktasoft.animatedconnectionobserver.sample.model.PreviewSettings

private val PreviewShape = RoundedCornerShape(24.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SampleScreen() {
    val realConnection by observeConnectivity()
    var useRealConnection by rememberSaveable { mutableStateOf(true) }
    var simulatedConnection by rememberSaveable { mutableStateOf(false) }
    var settings by rememberSaveable(stateSaver = PreviewSettings.Saver) { mutableStateOf(PreviewSettings()) }
    var previewClickCount by rememberSaveable { mutableIntStateOf(0) }
    val effectiveConnection = if (useRealConnection) realConnection else simulatedConnection
    val context = LocalContext.current
    val settingsListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AnimatedConnectivityBox(
                    isConnected = effectiveConnection,
                    blurWhenOffline = settings.blurWhenOffline,
                    blurRadius = settings.blurRadius.dp,
                    overlayWhenOffline = settings.overlayWhenOffline,
                    overlayAlphaWhenOffline = settings.overlayPercent / 100f,
                    messageBarMessageOffline = stringResource(R.string.message_offline),
                    messageBarMessageBackOnline = stringResource(R.string.message_back_online),
                    messageBarDelay = 250L,
                    cardTitleOffline = stringResource(R.string.card_title_offline),
                    cardMessageOffline = stringResource(R.string.card_message_offline),
                    cardDelay = 650L,
                    dampingRatioBouncy = settings.dampingRatio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                        .clip(PreviewShape)
                        .background(color = MaterialTheme.colorScheme.surfaceContainer)
                        .border(
                            width = 1.dp,
                            color =  MaterialTheme.colorScheme.outlineVariant,
                            shape = PreviewShape,
                        ),
                    navigationButton = {
                        TextButton(
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                            },
                        ) {
                            Text(text = stringResource(R.string.open_network_settings))
                        }
                    },
                ) {
                    DemoContent(
                        clickCount = previewClickCount,
                        onButtonClick = { previewClickCount++ },
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                LazyColumn(
                    state = settingsListState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        ConnectionSourceCard(
                            useRealConnection = useRealConnection,
                            realConnection = realConnection,
                            simulatedConnection = simulatedConnection,
                            effectiveConnection = effectiveConnection,
                            onUseRealConnectionChange = { useRealConnection = it },
                            onSimulatedConnectionChange = { simulatedConnection = it },
                        )
                    }
                    item {
                        SettingsCard(
                            settings = settings,
                            onSettingsChange = { settings = it },
                            onReset = { settings = PreviewSettings() },
                        )
                    }
                }
                VerticalScrollbar(
                    state = settingsListState,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp, vertical = 10.dp)
                        .width(4.dp),
                )
            }
        }
    }
}
