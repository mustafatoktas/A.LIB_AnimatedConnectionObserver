package com.toktasoft.animatedconnectionobserver.presentation.main.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTAB(
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Column {
        LargeTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = "Connection Observer",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            },
        )
        HorizontalDivider()
    }
}
