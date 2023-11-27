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

fun List<ChecklistItem>.addNewChecklistItem(): List<ChecklistItem> {
    return this.toMutableList()
        .apply { add(ChecklistItem.newInstance()) }
}

fun List<ChecklistItem>.removeChecklistItem(item: ChecklistItem): List<ChecklistItem> {
    return this.toMutableList()
        .apply { remove(item) }
}

fun List<ChecklistItem>.toggleAll(isChecked: Boolean): List<ChecklistItem> {
    return this.map { item -> item.copy(isChecked = isChecked) }
}