package com.hellguy39.hellnotes.note_detail.util

import android.text.format.DateFormat
import java.util.*

internal fun Long.formatAsLastEditDate(): String {
    val time = Calendar.getInstance()
    time.timeInMillis = this

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
        DateFormat.format("MMMM dd yyyy, h:mm aa", time).toString()
    }
}