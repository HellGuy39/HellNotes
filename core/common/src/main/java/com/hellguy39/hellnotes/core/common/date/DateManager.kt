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
package com.hellguy39.hellnotes.core.common.date

import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateManager private constructor(
    private val localDateTime: LocalDateTime,
) {
    fun isInPast(): Boolean {
        val currentMillis = currentMillis()
        val handledDateMillis = this.toMillis()
        return handledDateMillis < currentMillis
    }

    fun isInFeature(): Boolean {
        val currentMillis = currentMillis()
        val handledDateMillis = this.toMillis()
        return handledDateMillis > currentMillis
    }

    fun toMillis(): Long {
        return localDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun toLocalDateTime(): LocalDateTime {
        return localDateTime
    }

    fun format(pattern: String): String {
        return localDateTime.atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    fun formatBest(): String {
        val currentLocalDateTime = LocalDateTime.now()
        val handledLocalDateTime = this.localDateTime

        return when {
            currentLocalDateTime.year != handledLocalDateTime.year -> {
                format(DefaultPatterns.YEAR_MONTH_DAY_TIME)
            }
            currentLocalDateTime.month != handledLocalDateTime.month -> {
                format(DefaultPatterns.MONTH_DAY_TIME)
            }
            currentLocalDateTime.dayOfMonth != handledLocalDateTime.dayOfMonth -> {
                format(DefaultPatterns.MONTH_DAY_TIME)
            }
            else -> {
                format(DefaultPatterns.TIME_ONLY)
            }
        }
    }

    fun increase(repeat: Repeat): DateManager {
        return apply {
            when (repeat) {
                Repeat.Daily -> {
                    increaseDays(1)
                }
                Repeat.Weekly -> {
                    increaseWeeks(1)
                }
                Repeat.Monthly -> {
                    increaseMonths(1)
                }
                else -> Unit
            }
        }
    }

    fun increaseDays(days: Long): DateManager {
        return apply { localDateTime.plusDays(days) }
    }

    fun increaseWeeks(weeks: Long): DateManager {
        return apply { localDateTime.plusWeeks(weeks) }
    }

    fun increaseMonths(months: Long): DateManager {
        return apply { localDateTime.plusMonths(months) }
    }

    object DefaultPatterns {
        const val TIME_ONLY = "HH:mm"
        const val DATE_ONLY = "MMMM dd"

        const val MONTH_DAY_TIME = "dd MMMM, HH:mm"
        const val YEAR_MONTH_DAY_TIME = "dd MMMM yyyy, HH:mm"
        const val YEAR_MONTH_DAY = "dd.MM.yyyy"
    }

    object SpecialPatterns {
        const val ISO_8061 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val NEW_FILE = "MMM_dd_yyyy_HH_mm_ss"
        const val CHANGELOG_RELEASE = "dd/MM/yyyy"
    }

    companion object {
        fun currentMillis() = System.currentTimeMillis()

        fun now(): DateManager {
            return DateManager(LocalDateTime.now())
        }

        fun from(localDateTime: LocalDateTime): DateManager {
            return DateManager(localDateTime)
        }

        fun from(millis: Long): DateManager {
            val localDateTime =
                Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            return DateManager(localDateTime)
        }

        fun from(value: String?, pattern: String): DateManager {
            val formatter =
                DateTimeFormatter.ofPattern(pattern)
                    .withZone(ZoneId.systemDefault())
            val localDateTime = LocalDateTime.parse(value, formatter)
            return DateManager(localDateTime)
        }
    }
}
