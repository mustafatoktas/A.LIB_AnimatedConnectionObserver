package com.toktasoft.animatedconnectionobserver.sample

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.common.DampingRatioBouncy
import com.toktasoft.animatedconnectionobserver.composables.AnimatedConnectivityBox
import com.toktasoft.animatedconnectionobserver.composables.observeConnectivity
import com.toktasoft.animatedconnectionobserver.sample.composables.ConnectionSourceCard
import com.toktasoft.animatedconnectionobserver.sample.composables.DemoContent
import com.toktasoft.animatedconnectionobserver.sample.composables.SettingsCard
import com.toktasoft.animatedconnectionobserver.sample.composables.VerticalScrollbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SampleScreen() {
    val realConnection by observeConnectivity()
    var useRealConnection by rememberSaveable { mutableStateOf(true) }
    var simulatedConnection by rememberSaveable { mutableStateOf(false) }
    var blurWhenOffline by rememberSaveable { mutableStateOf(true) }
    var overlayWhenOffline by rememberSaveable { mutableStateOf(true) }
    var useTurkishMessages by rememberSaveable { mutableStateOf(true) }
    var blurRadius by rememberSaveable { mutableFloatStateOf(14f) }
    var overlayAlpha by rememberSaveable { mutableFloatStateOf(0.10f) }
    var previewClickCount by rememberSaveable { mutableIntStateOf(0) }
    var dampingRatioName by rememberSaveable {
        mutableStateOf(DampingRatioBouncy.MEDIUM_HIGH.name)
    }
    val effectiveConnection = if (useRealConnection) realConnection else simulatedConnection
    val dampingRatio = DampingRatioBouncy.valueOf(dampingRatioName)
    val context = LocalContext.current
    val settingsListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Animated Connection Observer")
                        Text(
                            text = "Etkileşimli örnek",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
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
                Text(
                    text = "Canlı önizleme",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                AnimatedConnectivityBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    isConnected = effectiveConnection,
                    blurWhenOffline = blurWhenOffline,
                    blurRadius = blurRadius.dp,
                    overlayWhenOffline = overlayWhenOffline,
                    overlayAlphaWhenOffline = overlayAlpha,
                    messageBarMessageOffline = if (useTurkishMessages) {
                        "İnternet bağlantısı yok"
                    } else {
                        "No connection"
                    },
                    messageBarMessageBackOnline = if (useTurkishMessages) {
                        "Yeniden çevrimiçisin"
                    } else {
                        "Back online"
                    },
                    messageBarDelay = 250L,
                    cardTitleOffline = if (useTurkishMessages) {
                        "Bağlantı kesildi"
                    } else {
                        "No internet connection"
                    },
                    cardMessageOffline = if (useTurkishMessages) {
                        "Ağ ayarlarını kontrol edip tekrar deneyebilirsin."
                    } else {
                        "Check your network settings and try again."
                    },
                    cardDelay = 650L,
                    dampingRatioBouncy = dampingRatio,
                    navigationButton = {
                        TextButton(
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                            },
                        ) {
                            Text(if (useTurkishMessages) "Ağ ayarlarını aç" else "Open network settings")
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
                            blurWhenOffline = blurWhenOffline,
                            onBlurWhenOfflineChange = { blurWhenOffline = it },
                            overlayWhenOffline = overlayWhenOffline,
                            onOverlayWhenOfflineChange = { overlayWhenOffline = it },
                            useTurkishMessages = useTurkishMessages,
                            onUseTurkishMessagesChange = { useTurkishMessages = it },
                            blurRadius = blurRadius,
                            onBlurRadiusChange = { blurRadius = it },
                            overlayAlpha = overlayAlpha,
                            onOverlayAlphaChange = { overlayAlpha = it },
                            dampingRatio = dampingRatio,
                            onDampingRatioChange = { dampingRatioName = it.name },
                            onReset = {
                                blurWhenOffline = true
                                overlayWhenOffline = true
                                useTurkishMessages = true
                                blurRadius = 14f
                                overlayAlpha = 0.10f
                                dampingRatioName = DampingRatioBouncy.MEDIUM_HIGH.name
                            },
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
