package com.hellguy39.hellnotes.notes.util

import android.text.format.DateFormat
import java.util.*

internal fun Long.formatAsLastEditDate(): String {
    return DateFormat.format("yyyy/MM/dd hh:mm:ss a", Date(this)).toString()
}