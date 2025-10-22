package com.toktasoft.animatedconnectionobserver.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.toktasoft.animatedconnectionobserver.R
import com.toktasoft.animatedconnectionobserver.common.DeviceStatus
import com.toktasoft.animatedconnectionobserver.common.RootOrEmulatorScreen
import com.toktasoft.animatedconnectionobserver.common.isEmulator
import com.toktasoft.animatedconnectionobserver.common.isRooted
import com.toktasoft.animatedconnectionobserver.presentation.main.MainRoute
import com.toktasoft.animatedconnectionobserver.presentation.ui.AnimatedConnectionObserverTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            var deviceStatus by remember { mutableStateOf(DeviceStatus.NORMAL) }

            LaunchedEffect(Unit) {
                launch(Dispatchers.Default) {
                    val isRootedDeferred = async { isRooted() }
                    val isEmulatorDeferred = async { isEmulator() }

                    val isRooted = isRootedDeferred.await()
                    val isEmulator = isEmulatorDeferred.await()

                    deviceStatus = when {
                        isRooted -> DeviceStatus.ROOTED
                        isEmulator -> DeviceStatus.EMULATOR
                        else -> DeviceStatus.NORMAL
                    }
                }
            }

            AnimatedConnectionObserverTheme {
                when(deviceStatus) {
                    DeviceStatus.ROOTED -> {
                        RootOrEmulatorScreen(
                            title = "Root Detected",
                            description = "For your security and to maintain app integrity, this app is not supported on devices with root access. Please unroot your device and try again.",
                            image = R.drawable.img_root,
                        )
                    }
                    DeviceStatus.EMULATOR -> {
                        RootOrEmulatorScreen(
                            title = "Emulator Detected",
                            description = "This app is designed for use on physical devices. An emulator has been detected, please run the app on a physical device and try again.",
                            image = R.drawable.img_emulator,
                        )
                    }
                    DeviceStatus.NORMAL -> { MainRoute() }
                }
            }
        }
    }
}
