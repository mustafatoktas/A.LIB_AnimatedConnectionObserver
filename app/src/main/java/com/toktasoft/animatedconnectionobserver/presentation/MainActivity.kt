package com.toktasoft.animatedconnectionobserver.presentation

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.toktasoft.animatedconnectionobserver.presentation.main.MainRoute
import com.toktasoft.animatedconnectionobserver.presentation.ui.AnimatedConnectionObserverTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val config = resources.configuration
        val smallestWidthDp = config.smallestScreenWidthDp
        requestedOrientation = if (smallestWidthDp < 600) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        enableEdgeToEdge()

        setContent {
            AnimatedConnectionObserverTheme {
                MainRoute()
            }
        }
    }
}
