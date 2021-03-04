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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.util.prettyPrintRemainingTime
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun Timer(remainingTime: Long, totalTime: Long, onStopTimer: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(48.dp)
            .aspectRatio(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        ProgressCircle(
            remainingTime = remainingTime,
            totalTime = totalTime,
        )
        RemainingTimeText(
            remainingTime = remainingTime,
            totalTime = totalTime,
        )
        if (remainingTime > 0) {
            CancelButton(onClick = onStopTimer)
        }
    }
}

@Composable
private fun ProgressCircle(remainingTime: Long, totalTime: Long) {
    val progress = remainingTime / totalTime.coerceAtLeast(1).toFloat()
    CircularProgressIndicator(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.onBackground,
        progress = 1f,
    )
    CircularProgressIndicator(
        modifier = Modifier.fillMaxSize(),
        progress = progress,
    )
}

@Composable
private fun RemainingTimeText(remainingTime: Long, totalTime: Long) {
    Text(
        text = when {
            totalTime == 0L -> "Enter duration"
            remainingTime == 0L -> "Completed"
            else -> remainingTime.prettyPrintRemainingTime()
        },
        style = when {
            remainingTime > 0 -> MaterialTheme.typography.h2
            else -> MaterialTheme.typography.h4
        }
    )
}

@Composable
private fun CancelButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        TextButton(onClick) {
            Text(
                "Cancel",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview("Light Theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LightPreview() {
    MdcTheme {
        Timer(remainingTime = 3500L, totalTime = 5000L, onStopTimer = {})
    }
}

@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    MdcTheme {
        Timer(remainingTime = 3500L, totalTime = 5000L, onStopTimer = {})
    }
}
