package com.hellguy39.hellnotes.core.ui

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtils {
    fun localDateTimeToEpochMillis(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun formatEpochMillis(epochMilli: Long, pattern: String): String {
        return Instant.ofEpochMilli(epochMilli)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    fun formatLocalDateTime(localDateTime: LocalDateTime, pattern: String): String {
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

        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(millis),
            ZoneId.systemDefault()
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

    fun increaseDays(millis: Long, days: Long): Long {
        val localDateTime = epochMillisToLocalDateTime(millis)
        return localDateTimeToEpochMillis(localDateTime.plusDays(days))
    }

    fun increaseWeeks(millis: Long, weeks: Long): Long {
        val localDateTime = epochMillisToLocalDateTime(millis)
        return localDateTimeToEpochMillis(localDateTime.plusWeeks(weeks))
    }

    fun increaseMonths(millis: Long, months: Long): Long {
        val localDateTime = epochMillisToLocalDateTime(millis)
        return localDateTimeToEpochMillis(localDateTime.plusMonths(months))
    }

    const val TIME_PATTERN = "HH:mm"
    const val DATE_PATTERN = "MMMM dd"

    private const val DATE_PATTERN_YEAR_MONTH_DAY_TIME = "MMM dd, yyyy, HH:mm"
    private const val DATE_PATTERN_MONTH_DAY_TIME = "MMM dd, HH:mm"
    private const val DATE_PATTERN_TIME = "HH:mm"

}