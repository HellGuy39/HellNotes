package com.hellguy39.hellnotes.note_detail.events

import com.hellguy39.hellnotes.model.Remind

interface EditReminderDialogEvents {

    fun show()
    fun dismiss()
    fun setRemindToEdit(remind: Remind)
    fun deleteRemind(remind: Remind)
    fun updateRemind(remind: Remind)

}