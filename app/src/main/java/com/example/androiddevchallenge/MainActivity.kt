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
package com.example.androiddevchallenge

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.widgets.AppBar
import com.example.androiddevchallenge.ui.widgets.DurationPicker
import com.example.androiddevchallenge.ui.widgets.Timer
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch

private const val TICKER_UPDATE_DELAY_MS = 15L // 60 FPS

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                MyApp(BottomSheetValue.Expanded)
            }
        }
    }
}

// Start building your app here!
@OptIn(ExperimentalMaterialApi::class, ObsoleteCoroutinesApi::class)
@Composable
fun MyApp(initialState: BottomSheetValue = BottomSheetValue.Collapsed) {
    var remainingTime by remember { mutableStateOf(0L) }
    var totalTime by remember { mutableStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialState)
    )

    val onStartTimer: (Long) -> Unit = { durationMs ->
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()

            totalTime = durationMs
            remainingTime = totalTime
            val ticker = ticker(TICKER_UPDATE_DELAY_MS)
            while (remainingTime > 0) {
                ticker.receive()
                remainingTime -= TICKER_UPDATE_DELAY_MS
            }
            remainingTime = 0
            ticker.cancel()

            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        topBar = { AppBar(stringResource(R.string.app_name)) },
        sheetContent = { DurationPicker(onStartTimer = onStartTimer) },
        sheetPeekHeight = 0.dp,
        sheetGesturesEnabled = false,
        scaffoldState = bottomSheetScaffoldState,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Timer(
                remainingTime = remainingTime,
                totalTime = totalTime,
                onStopTimer = { remainingTime = 0 }
            )
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun LightPreview() {
    MdcTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    MdcTheme {
        MyApp()
    }
}
