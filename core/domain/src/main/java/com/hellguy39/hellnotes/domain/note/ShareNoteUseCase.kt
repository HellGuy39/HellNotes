package com.hellguy39.hellnotes.domain.note

import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.model.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ShareNoteUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(note: Note, onEmptyNote: () -> Unit)  {
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
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Subject"
        )
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        context.startActivity(Intent.createChooser(intent, "Share"))
    }
}