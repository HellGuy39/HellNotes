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

data class ChecklistItem(
    val isChecked: Boolean,
    val text: String,
) {
    companion object {
        fun newInstance() =
            ChecklistItem(
                isChecked = false,
                text = "",
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
