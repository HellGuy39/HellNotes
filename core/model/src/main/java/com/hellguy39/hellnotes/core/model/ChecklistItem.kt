package com.hellguy39.hellnotes.core.model

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