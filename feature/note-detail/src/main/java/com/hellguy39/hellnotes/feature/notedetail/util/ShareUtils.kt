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
package com.hellguy39.hellnotes.feature.notedetail.util

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import java.io.File

object ShareUtils {
    fun share(
        context: Context,
        note: Note,
        type: ShareType,
    ) {
        val shareIntent =
            when (type) {
                is ShareType.PlainText -> {
                    sharePlainTextIntent(note)
                }
                is ShareType.TxtFile -> {
                    val file = context.createTempFile()
                    file.writeText(text = note.buildContent(), charset = Charsets.UTF_8)
                    context.shareTxtFileIntent(file)
                }
            }
        ContextCompat.startActivity(
            context,
            shareIntent,
            null,
        )
    }

    private fun sharePlainTextIntent(note: Note): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, note.buildContent())
        intent.type = TYPE_PLAIN_TEXT
        return Intent.createChooser(intent, null)
    }

    private fun Context.shareTxtFileIntent(file: File): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = TYPE_TXT_FILE
        intent.putExtra(
            Intent.EXTRA_STREAM,
            FileProvider.getUriForFile(this, this.packageName + ".provider", file),
        )
        return intent
    }

    private fun Context.createTempFile(): File {
        return File(
            getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "note_" + System.currentTimeMillis().toString() + ".txt",
        )
    }

    private fun Note.buildContent(): String {
        val date = DateFormat.format("MMMM dd yyyy, h:mm aa", editedAt).toString()
        return "${this.title}\n\n${this.note}\n\n$date"
    }

    private const val TYPE_PLAIN_TEXT = "text/plain"
    private const val TYPE_TXT_FILE = "text/*"
}

sealed interface ShareType {
    data object PlainText : ShareType

    data object TxtFile : ShareType
}
