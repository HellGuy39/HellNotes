package com.hellguy39.hellnotes.core.model

data class Checklist(
    val id: Long? = null,
    val name: String,
    val noteId: Long,
    val items: List<ChecklistItem>
) {
    companion object {
        fun initialInstance(
            name: String = "",
            noteId: Long = -1,
            items: List<ChecklistItem> = listOf()
        ) = Checklist(
            name = name,
            noteId = noteId,
            items = items
        )
    }
}

fun Checklist.isChecklistValid(): Boolean {
    return this.name.isNotBlank() || this.name.isNotEmpty() || this.items.isItemsValid()
}

fun List<Checklist>.isChecklistsValid(): Boolean {
    for (i in this.indices) {
        if (this[i].isChecklistValid()) {
            return true
        }
    }
    return false
}

fun List<Checklist>.sortByPriority(): List<Checklist> {
    return this.toMutableList()
        .apply {
            sortByDescending { checklist ->
                checklist.items.countOfUncheckedItem()
            }
        }
}

fun List<Checklist>.removeCompletedChecklists(): List<Checklist> {
    return this.toMutableList()
        .apply {
            removeIf { checklist ->
                checklist.items.countOfUncheckedItem() == 0
            }
        }
}

fun List<ChecklistItem>.countOfUncheckedItem(): Int {
    return this.toMutableList().filter { item -> !item.isChecked }.size
}

fun List<ChecklistItem>.isItemsValid(): Boolean {
    for (i in this.indices) {
        if (this[i].text.isNotBlank() && this[i].text.isNotEmpty()) {
            return true
        }
    }
    return false
}