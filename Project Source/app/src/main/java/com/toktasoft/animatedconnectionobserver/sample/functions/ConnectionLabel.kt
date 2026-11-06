package com.toktasoft.animatedconnectionobserver.sample.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.toktasoft.animatedconnectionobserver.sample.R

@Composable
internal fun connectionLabel(isConnected: Boolean): String =
    stringResource(if (isConnected) R.string.online else R.string.offline)
