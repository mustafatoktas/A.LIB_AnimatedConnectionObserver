package com.toktasoft.animatedconnectionobserver.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOff
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LayersClear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WifiFind
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.toktasoft.animatedconnectionobserver.composables.AnimatedConnectivityBox
import com.toktasoft.animatedconnectionobserver.composables.observeConnectivity
import com.toktasoft.animatedconnectionobserver.common.extensions.openInBrowser
import com.toktasoft.animatedconnectionobserver.common.extensions.openWifiSettings
import com.toktasoft.animatedconnectionobserver.presentation.main.composables.MainFAB
import com.toktasoft.animatedconnectionobserver.presentation.main.composables.MainTAB
import com.toktasoft.animatedconnectionobserver.presentation.main.composables.RowWithSwitch
import com.toktasoft.animatedconnectionobserver.presentation.main.composables.WifiCard
import kotlinx.coroutines.launch

@Composable
fun MainRoute() {
    val isConnected by observeConnectivity()    // this composable comes from the Animated Connection Observer library
    val context = LocalContext.current
    var blurWhenOffline by remember { mutableStateOf(true) }
    var overlayWhenOffline by remember { mutableStateOf(true) }


    AnimatedConnectivityBox(                  // this composable comes from the Animated Connection Observer library
        modifier = Modifier.fillMaxSize(),
        isConnected = isConnected,
        blurWhenOffline = blurWhenOffline,
        overlayWhenOffline = overlayWhenOffline,
        navigationButton = {
            OutlinedButton(
                onClick = {
                    context.openWifiSettings()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.WifiFind,
                    contentDescription = null,
                )
            }
        },
    ) {
        MainScreen(
            isConnected = isConnected,
            blurState = blurWhenOffline,
            overlayState = overlayWhenOffline,
            changeBlurState = {
                blurWhenOffline = it
                if (it) overlayWhenOffline = true
            },
            changeOverlayState = {
                if (blurWhenOffline) return@MainScreen
                overlayWhenOffline = it
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isConnected: Boolean,
    blurState: Boolean,
    changeBlurState: (Boolean) -> Unit,
    overlayState: Boolean,
    changeOverlayState: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    val showSnackBar: () -> Unit = {
        scope.launch {
            snackBarHostState.showSnackbar(
                message = "Animated connection observer library demo app from Mustafa TOKTAŞ @Toktasoft",
                withDismissAction = true,
                duration = SnackbarDuration.Short,
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainTAB(
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            MainFAB (
                onClick = showSnackBar,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { pv ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            WifiCard(
                isConnected = isConnected,
                onClick = showSnackBar,
            )
            RowWithSwitch(
                title = "Mustafa TOKTAŞ",
                summary = "Open Developer's GitHub Profile",
                icon = Icons.Default.Person,
                isVisibleSwitch = false,
                onClick = {
                    context.openInBrowser("https://github.com/mustafatoktas")
                },
            )
            RowWithSwitch(
                title = "Library Repository",
                summary = "View Animated Connection Observer source code",
                icon = Icons.Default.Code,
                isVisibleSwitch = false,
                onClick = {
                    context.openInBrowser("https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver")
                },
            )
            RowWithSwitch(
                title = "Blur Effect",
                summary = "Applies a blur when offline. Overlay is required while blur is active.",
                icon = if (blurState) Icons.Default.BlurOn else Icons.Default.BlurOff,
                checked = blurState,
                onCheckedChange = changeBlurState,
            )
            RowWithSwitch(
                title = "Overlay Layer",
                summary = "Adds a semi-transparent layer that blocks touch when offline. Automatically enabled when Blur is active.",
                icon = if (overlayState) Icons.Default.Layers else Icons.Default.LayersClear,
                checked = overlayState,
                onCheckedChange = changeOverlayState,
            )
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
