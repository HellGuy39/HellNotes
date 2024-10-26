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
package com.hellguy39.hellnotes.core.common.arguments

sealed class Arguments<T>(
    val key: String,
    val emptyValue: T,
) {
    data object NoteId : Arguments<Long>("note_id", -1L)

    data object ReminderId : Arguments<Long>("reminder_id", -1L)

    data object LabelId : Arguments<Long>("label_id", -1L)

    data object NotificationId : Arguments<Int>("notification_id", -1)

    data object Message : Arguments<String>("message", "")

    data object Action : Arguments<String>("action", "")

    data object Type : Arguments<String>("type", "")

    fun isEmpty(value: T?) = value == null || value == emptyValue

    fun isNotEmpty(value: T?) = value != null && value != emptyValue
}
