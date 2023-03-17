package com.hellguy39.hellnotes.core.model

data class CheckItem(
    val position: Int,
    val isChecked: Boolean,
    val text: String
) {
    companion object {
        fun newInstance(position: Int) = CheckItem(
            position = position,
            isChecked = false,
            text = ""
        )
    }
}