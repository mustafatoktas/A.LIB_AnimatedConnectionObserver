package com.toktasoft.animatedconnectionobserver.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.toktasoft.animatedconnectionobserver.sample.ui.AnimatedConnectionObserverTheme

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimatedConnectionObserverTheme {
                SampleScreen()
            }
        }
    }
}
