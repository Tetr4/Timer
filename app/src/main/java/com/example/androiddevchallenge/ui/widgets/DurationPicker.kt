/*
 * Copyright 2021 The Android Open Source Project
 * Copyright 2021 Mike Klimek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.util.addDigit
import com.example.androiddevchallenge.util.dropDigit
import com.example.androiddevchallenge.util.prettyPrintDuration
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun DurationPicker(onStartTimer: (durationMs: Long) -> Unit) {
    var durationMs: Long by remember { mutableStateOf(0) }
    val onAddDigit = { digit: Int -> durationMs = addDigit(durationMs, digit) }
    val onDropDigit = { durationMs = dropDigit(durationMs) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DurationDisplay(durationMs = durationMs)
            Numpad(onAddDigit = onAddDigit)
        }
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            DeleteButton(onClick = onDropDigit)
            // TODO not sure why SpaceBetween does not work, so use padding here for now
            Spacer(Modifier.padding(top = 36.dp))
            PlayButton(onClick = { onStartTimer(durationMs) })
        }
    }
}

@Composable
private fun DurationDisplay(durationMs: Long) {
    Text(
        text = durationMs.prettyPrintDuration(),
        style = MaterialTheme.typography.h4,
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun DeleteButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            Icons.Outlined.Backspace,
            contentDescription = "Delete",
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun Numpad(onAddDigit: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Number(1, onAddDigit)
            Number(2, onAddDigit)
            Number(3, onAddDigit)
        }
        Row {
            Number(4, onAddDigit)
            Number(5, onAddDigit)
            Number(6, onAddDigit)
        }
        Row {
            Number(7, onAddDigit)
            Number(8, onAddDigit)
            Number(9, onAddDigit)
        }
        Row {
            Number(0, onAddDigit)
        }
    }
}

@Composable
private fun Number(number: Int, onAddDigit: (Int) -> Unit) {
    TextButton(
        onClick = { onAddDigit(number) },
    ) {
        Text(
            number.toString(),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            Icons.Outlined.PlayArrow,
            contentDescription = "Start",
            tint = MaterialTheme.colors.primary
        )
    }
}

@Preview("Light Theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LightPreview() {
    MdcTheme {
        DurationPicker(onStartTimer = {})
    }
}

@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    MdcTheme {
        DurationPicker(onStartTimer = {})
    }
}
