/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.ui

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Migrate to DateManager
@Deprecated("Use DateManager from :core:common instead")
object DateTimeUtils {
    fun iso8061toLocalDateTime(date: String): LocalDateTime {
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.systemDefault())
        return LocalDateTime.parse(date, formatter)
    }

    fun localDateTimeToEpochMillis(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun formatEpochMillis(
        epochMilli: Long,
        pattern: String,
    ): String {
        return Instant.ofEpochMilli(epochMilli)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    fun formatLocalDateTime(
        localDateTime: LocalDateTime,
        pattern: String,
    ): String {
        return localDateTime.atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    fun getCurrentTimeInEpochMilli(): Long {
        return LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun epochMillisToLocalDateTime(epochMilli: Long): LocalDateTime {
        return Instant.ofEpochMilli(epochMilli)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun formatBest(millis: Long): String {
        val time = Calendar.getInstance()
        time.timeInMillis = millis
        val now = Calendar.getInstance()

        val localDateTime =
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(millis),
                ZoneId.systemDefault(),
            )

        return if (now.get(Calendar.YEAR) != time.get(Calendar.YEAR)) {
            localDateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN_YEAR_MONTH_DAY_TIME))
        } else if (now.get(Calendar.MONTH) != time.get(Calendar.MONTH)) {
            localDateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN_MONTH_DAY_TIME))
        } else if (now.get(Calendar.DAY_OF_MONTH) != time.get(Calendar.DAY_OF_MONTH)) {
            localDateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN_MONTH_DAY_TIME))
        } else {
            localDateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN_TIME))
        }
    }

    fun increaseDays(
        millis: Long,
        days: Long,
    ): Long {
        val localDateTime = epochMillisToLocalDateTime(millis)
        return localDateTimeToEpochMillis(localDateTime.plusDays(days))
    }

    fun increaseWeeks(
        millis: Long,
        weeks: Long,
    ): Long {
        val localDateTime = epochMillisToLocalDateTime(millis)
        return localDateTimeToEpochMillis(localDateTime.plusWeeks(weeks))
    }

    fun increaseMonths(
        millis: Long,
        months: Long,
    ): Long {
        val localDateTime = epochMillisToLocalDateTime(millis)
        return localDateTimeToEpochMillis(localDateTime.plusMonths(months))
    }

    const val NEW_FILE_PATTERN = "MMM_dd_yyyy_HH_mm_ss"
    const val TIME_PATTERN = "HH:mm"
    const val DATE_PATTERN = "MMMM dd"
    const val CHANGELOG_RELEASE_PATTERN = "dd/MM/yyyy"
    const val DATE_TIME_PATTERN = "MMMM dd, yyyy"
    const val FULL_DATE_PATTERN = "MMMM dd, yyyy (HH:mm)"

    private const val DATE_PATTERN_YEAR_MONTH_DAY_TIME = "MMM dd, yyyy, HH:mm"
    private const val DATE_PATTERN_MONTH_DAY_TIME = "MMM dd, HH:mm"
    private const val DATE_PATTERN_TIME = "HH:mm"
}
