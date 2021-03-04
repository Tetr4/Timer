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

import com.example.androiddevchallenge.util.addDigit
import com.example.androiddevchallenge.util.dropDigit
import com.example.androiddevchallenge.util.hours
import com.example.androiddevchallenge.util.minutes
import com.example.androiddevchallenge.util.parseDuration
import com.example.androiddevchallenge.util.prettyPrintDuration
import com.example.androiddevchallenge.util.seconds
import com.example.androiddevchallenge.util.serializeDuration
import org.junit.Assert
import org.junit.Test

private const val MILLIS_PER_SECOND = 1000L
private const val MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND
private const val MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE

class DurationLogicTest {
    @Test
    fun test_seconds() {
        Assert.assertEquals(1, (1 * MILLIS_PER_SECOND).seconds)
        Assert.assertEquals(2, (2 * MILLIS_PER_SECOND).seconds)
        Assert.assertEquals(0, (0.8 * MILLIS_PER_SECOND).toLong().seconds)
        Assert.assertEquals(1, (1.2 * MILLIS_PER_SECOND).toLong().seconds)
    }

    @Test
    fun test_minutes() {
        Assert.assertEquals(0, (0 * MILLIS_PER_MINUTE).minutes)
        Assert.assertEquals(20, (20 * MILLIS_PER_MINUTE).minutes)
        Assert.assertEquals(59, (59 * MILLIS_PER_MINUTE).minutes)
    }

    @Test
    fun test_hours() {
        Assert.assertEquals(0, (0 * MILLIS_PER_HOUR).hours)
        Assert.assertEquals(1, (1 * MILLIS_PER_HOUR).hours)
        Assert.assertEquals(2, (2 * MILLIS_PER_HOUR).hours)
        Assert.assertEquals(500, (500 * MILLIS_PER_HOUR).hours)
        Assert.assertEquals(0, (50 * MILLIS_PER_MINUTE).hours)
        Assert.assertEquals(1, (70 * MILLIS_PER_MINUTE).hours)
        Assert.assertEquals(1, (110 * MILLIS_PER_MINUTE).hours)
    }

    @Test
    fun test_prettyPrintDuration() {
        Assert.assertEquals("0h 00m 00s", 0L.prettyPrintDuration())
        Assert.assertEquals("0h 00m 01s", (1 * MILLIS_PER_SECOND).prettyPrintDuration())
        Assert.assertEquals("0h 00m 10s", (10 * MILLIS_PER_SECOND).prettyPrintDuration())
        Assert.assertEquals("0h 10m 00s", (10 * MILLIS_PER_MINUTE).prettyPrintDuration())
        Assert.assertEquals("7h 00m 00s", (7 * MILLIS_PER_HOUR).prettyPrintDuration())
        Assert.assertEquals("500h 00m 00s", (500 * MILLIS_PER_HOUR).prettyPrintDuration())
    }

    @Test
    fun test_serializeDuration() {
        Assert.assertEquals("00000", 0L.serializeDuration())
        Assert.assertEquals("00001", (1 * MILLIS_PER_SECOND).serializeDuration())
        Assert.assertEquals("00010", (10 * MILLIS_PER_SECOND).serializeDuration())
        Assert.assertEquals("01000", (10 * MILLIS_PER_MINUTE).serializeDuration())
        Assert.assertEquals("70000", (7 * MILLIS_PER_HOUR).serializeDuration())
        Assert.assertEquals("5000000", (500 * MILLIS_PER_HOUR).serializeDuration())
    }

    @Test
    fun test_parseDuration() {
        Assert.assertEquals(0, "00000".parseDuration())
        Assert.assertEquals(1 * MILLIS_PER_SECOND, "00001".parseDuration())
        Assert.assertEquals(10 * MILLIS_PER_SECOND, "00010".parseDuration())
        Assert.assertEquals(10 * MILLIS_PER_MINUTE, "01000".parseDuration())
        Assert.assertEquals(7 * MILLIS_PER_HOUR, "70000".parseDuration())
        Assert.assertEquals(500 * MILLIS_PER_HOUR, "5000000".parseDuration())
    }

    @Test
    fun test_dropDigit() {
        Assert.assertEquals(1 * MILLIS_PER_SECOND, dropDigit(10 * MILLIS_PER_SECOND))
        Assert.assertEquals(30 * MILLIS_PER_SECOND, dropDigit(3 * MILLIS_PER_MINUTE))
        Assert.assertEquals(50 * MILLIS_PER_MINUTE, dropDigit(5 * MILLIS_PER_HOUR))
    }

    @Test
    fun test_addDigit() {
        Assert.assertEquals(23 * MILLIS_PER_SECOND, addDigit(2 * MILLIS_PER_SECOND, 3))
        Assert.assertEquals(1 * MILLIS_PER_MINUTE + 23 * MILLIS_PER_SECOND, addDigit(12 * MILLIS_PER_SECOND, 3))
        Assert.assertEquals(66 * MILLIS_PER_SECOND, addDigit(6 * MILLIS_PER_SECOND, 6))
    }
}
