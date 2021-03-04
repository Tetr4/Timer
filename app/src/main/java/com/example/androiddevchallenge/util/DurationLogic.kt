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
package com.example.androiddevchallenge.util

import java.lang.IllegalArgumentException

val Long.seconds get() = (this / 1000) % 60
val Long.minutes get() = this / (1000 * 60) % 60
val Long.hours get() = this / (1000 * 60 * 60)

fun dropDigit(durationMs: Long): Long {
    // shift right and add a leading "0"
    val serializeDuration = "0" + durationMs.serializeDuration().dropLast(1)
    return serializeDuration.parseDuration()
}

fun addDigit(durationMs: Long, digit: Int): Long {
    if (digit !in 0..9) throw IllegalArgumentException("single place digit is required")
    // add a digit
    val serializeDuration = durationMs.serializeDuration().drop(1) + digit
    return serializeDuration.parseDuration()
}

fun String.parseDuration(): Long {
    val seconds = substring(length - 2, length).toInt()
    val minutes = substring(length - 4, length - 2).toInt()
    val hours = substring(0, length - 4).toInt()
    return seconds * 1000L +
        minutes * 60 * 1000L +
        hours * 60 * 60 * 1000L
}

fun Long.serializeDuration(): String {
    val hours = this.hours.toString()
    val minutes = this.minutes.toString().padStart(2, '0')
    val seconds = this.seconds.toString().padStart(2, '0')
    return "$hours$minutes$seconds"
}

fun Long.prettyPrintDuration(): String {
    val hours = this.hours.toString()
    val minutes = this.minutes.toString().padStart(2, '0')
    val seconds = this.seconds.toString().padStart(2, '0')
    return "${hours}h ${minutes}m ${seconds}s"
}

fun Long.prettyPrintRemainingTime(): String {
    if (hours > 0) return "${hours}h"
    if (minutes > 0) return "${minutes}m"
    return "${seconds + 1}s" // + 1 so it feels more natural
}
