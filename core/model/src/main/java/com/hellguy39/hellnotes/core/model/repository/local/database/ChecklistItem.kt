package com.hellguy39.hellnotes.core.model.repository.local.database

data class ChecklistItem(
    val isChecked: Boolean,
    val text: String
) {
    companion object {
        fun newInstance() = ChecklistItem(
            isChecked = false,
            text = ""
        )
    }
}