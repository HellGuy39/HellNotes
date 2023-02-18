package com.hellguy39.hellnotes.core.ui

import android.content.Context
import android.text.format.DateFormat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DateHelper @Inject constructor(
    @ApplicationContext context: Context
) {
    private val formatter: DateTimeFormatter
    private val zoneId: ZoneId = ZoneId.systemDefault()

    init {
        val locale = context.resources.configuration.locales.get(0)
        formatter = DateTimeFormatter.ofPattern(DATE_TIME_SHORT_PATTERN, locale)
        formatter.withZone(zoneId)
    }

    fun dateToEpochMillis(localTime: LocalTime, localDate: LocalDate): Long {
        val instant = localTime.atDate(localDate)
            .atZone(zoneId)
            .toInstant()

        return instant.toEpochMilli()
    }

    fun dateToEpochMillis(localDateTime: LocalDateTime): Long {
        val instant = localDateTime
            .atZone(zoneId)
            .toInstant()

        return instant.toEpochMilli()
    }

    fun epochMillisToFormattedDate(epochMilli: Long): String {
        val localeDateTime = Instant.ofEpochMilli(epochMilli)
            .atZone(zoneId)
            .toLocalDateTime()

        return localeDateTime.format(formatter)
    }

    fun getCurrentTimeInEpochMilli(): Long {
        return LocalDateTime.now()
            .atZone(zoneId)
            .toInstant()
            .toEpochMilli()
    }

    fun epochMillisToLocalTime(epochMilli: Long): LocalTime {
        return Instant.ofEpochMilli(epochMilli)
            .atZone(zoneId)
            .toLocalTime()
    }

    fun epochMillisToLocalDate(epochMilli: Long): LocalDate {
        return Instant.ofEpochMilli(epochMilli)
            .atZone(zoneId)
            .toLocalDate()
    }

    fun epochMillisToLocalDateTime(epochMilli: Long): LocalDateTime {
        return Instant.ofEpochMilli(epochMilli)
            .atZone(zoneId)
            .toLocalDateTime()
    }

    fun formatBest(millis: Long): String {
        val time = Calendar.getInstance()
        time.timeInMillis = millis

        val now = Calendar.getInstance()

        return if (now.get(Calendar.DATE) == time.get(Calendar.DATE)) {
            DateFormat.format(TIME_PATTERN, time).toString()
        } else if (now.get(Calendar.DATE) - time.get(Calendar.DATE) == 1) {
            DateFormat.format(TIME_PATTERN, time).toString()
        } else if (now.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            DateFormat.format(DATE_TIME_PATTERN, time).toString()
        } else {
            DateFormat.format(DATE_TIME_PATTERN, time).toString()
        }
    }

    companion object {
        const val DATE_TIME_PATTERN = "MMMM dd yyyy HH:mm"
        const val DATE_TIME_SHORT_PATTERN = "MMM dd yyyy HH:mm"

        const val TIME_PATTERN = "HH:mm"

        const val DATE_PATTERN = "MMMM dd yyyy"
        const val DATE_SHORT_PATTERN = "MMM dd"
        const val DATE_LONG_PATTERN = "MMMM dd"
    }

}