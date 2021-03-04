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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.R
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun AppBar(title: String) {
    val onlyBottomCornerShape = MaterialTheme.shapes.medium.copy(
        topStart = CornerSize(0),
        topEnd = CornerSize(0),
    )
    TopAppBar(
        modifier = Modifier.clip(onlyBottomCornerShape),
        title = { Text(title) },
    )
}

@Preview("Light Theme", heightDp = 48, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LightPreview() {
    MdcTheme {
        AppBar(stringResource(R.string.app_name))
    }
}

@Preview("Dark Theme", heightDp = 48, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    MdcTheme {
        AppBar(stringResource(R.string.app_name))
    }
}
