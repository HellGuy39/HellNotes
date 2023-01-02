package com.hellguy39.hellnotes.note_detail.util

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.hellguy39.hellnotes.model.Note
import java.io.File
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class ShareHelper(
    private var context: Context
) {
    fun share(note: Note, type: ShareType) {
        val shareIntent = when(type) {
            is ShareType.PlainText -> {
                sharePlainTextIntent(note)
            }
            is ShareType.TxtFile -> {
                val file = createTempFile()
                file.writeText(text = note.buildContent(), charset = Charsets.UTF_8)
                shareTxtFileIntent(file)
            }
        }
        ContextCompat.startActivity(
            context,
            shareIntent,
            null
        )
    }

    private fun sharePlainTextIntent(note: Note): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, note.buildContent())
        intent.type = TYPE_PLAIN_TEXT
        return Intent.createChooser(intent, null)
    }

    private fun shareTxtFileIntent(file: File): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = TYPE_TXT_FILE
        intent.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        )
        return intent
    }

    private fun createTempFile(): File {
        return File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "note_" + System.currentTimeMillis().toString() + ".txt"
        )
    }

    private fun Note.buildContent(): String {
        val date = DateFormat.format("MMMM dd yyyy, h:mm aa", lastEditDate).toString()
        return "${this.title}\n\n${this.note}\n\n${date}"
    }

    companion object {
        private const val TYPE_PLAIN_TEXT = "text/plain"
        private const val TYPE_TXT_FILE = "text/*"
    }

}

sealed interface ShareType {
    object PlainText: ShareType
    object TxtFile: ShareType
}