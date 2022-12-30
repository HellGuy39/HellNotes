package com.hellguy39.hellnotes.note_detail.util

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import com.hellguy39.hellnotes.model.Note
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

internal fun shareNote(context: Context, note: Note, onEmptyNote: () -> Unit)  {
    val intent = Intent(Intent.ACTION_SEND)
    val title = note.title
    val content = note.note

    val shareBody = if ((title.isEmpty() || title.isBlank()) && (content.isNotEmpty() || content.isNotBlank()))
        note.note
    else if ((content.isEmpty() || content.isBlank()) && (title.isNotEmpty() || title.isNotBlank()))
        note.title
    else if (title.isNotEmpty() || title.isNotBlank() || content.isNotEmpty() || content.isNotBlank())
        "${note.title}\n\n${note.note}"
    else {
        onEmptyNote()
        return
    }

    intent.type = "text/plain"
//    intent.putExtra(
//        Intent.EXTRA_SUBJECT,
//        "Subject"
//    )
    intent.putExtra(Intent.EXTRA_TEXT, shareBody)
    context.startActivity(Intent.createChooser(intent, "Share"))
}