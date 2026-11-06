package com.toktasoft.animatedconnectionobserver.sample.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.toktasoft.animatedconnectionobserver.sample.R

@Composable
internal fun percentLabel(value: Int): String = stringResource(R.string.percent_value, value)
