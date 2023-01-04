package com.hellguy39.hellnotes.common.date

import android.content.Context
import android.text.format.DateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class DateHelper (
    context: Context
) {

    private val formatter: DateTimeFormatter
    private val zoneId: ZoneId = ZoneId.systemDefault()

    init {
        val locale = context.resources.configuration.locales.get(0)
        formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, locale)
        formatter.withZone(zoneId)
    }

    fun dateToEpochMillis(localTime: LocalTime, localDate: LocalDate): Long {
        val instant = localTime.atDate(localDate)
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

    fun formatAsLastEditDate(millis: Long): String {
        val time = Calendar.getInstance()
        time.timeInMillis = millis

        val now = Calendar.getInstance()

        val timeFormatString = "h:mm aa"
        val dateTimeFormatString = "EEEE, MMMM d, h:mm aa"

        return if (now.get(Calendar.DATE) == time.get(Calendar.DATE)) {
            DateFormat.format(timeFormatString, time).toString()
        } else if (now.get(Calendar.DATE) - time.get(Calendar.DATE) == 1) {
            DateFormat.format(timeFormatString, time).toString()
        } else if (now.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            DateFormat.format(dateTimeFormatString, time).toString()
        } else {
            DateFormat.format(DATE_PATTERN, time).toString()
        }
    }

    companion object {
        const val DATE_PATTERN = "dd MMMM yyyy HH:mm"
    }

}