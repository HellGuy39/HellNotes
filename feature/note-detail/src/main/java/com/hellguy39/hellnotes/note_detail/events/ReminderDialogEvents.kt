package com.hellguy39.hellnotes.note_detail.events

import com.hellguy39.hellnotes.model.Remind

interface ReminderDialogEvents {
    fun show()
    fun dismiss()
    fun onCreateRemind(remind: Remind)
}