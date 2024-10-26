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
package com.hellguy39.hellnotes.core.model.repository.local.database

data class Checklist(
    val id: Long? = null,
    val isExpanded: Boolean,
    val name: String,
    val noteId: Long,
    val items: List<ChecklistItem>,
) {
    companion object {
        fun initialInstance(
            name: String = "",
            isExpanded: Boolean = true,
            noteId: Long = -1,
            items: List<ChecklistItem> = listOf(),
        ) = Checklist(
            name = name,
            noteId = noteId,
            items = items,
            isExpanded = isExpanded,
        )
    }
}

fun Checklist.isChecklistValid(): Boolean {
    return this.name.isNotBlank() || this.name.isNotEmpty() || this.items.isItemsValid()
}

fun Checklist.isItemsCompleted(): Boolean {
    this.items.forEach { item ->
        if (!item.isChecked) {
            return false
        }
    }
    return true
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
