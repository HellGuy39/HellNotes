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
package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed interface Repeat {
    object DoesNotRepeat : Repeat

    object Daily : Repeat

    object Weekly : Repeat

    object Monthly : Repeat

    fun string(): String {
        return when (this) {
            is DoesNotRepeat -> DOES_NOT_REPEAT
            is Daily -> DAILY
            is Weekly -> WEEKLY
            is Monthly -> MONTHLY
        }
    }

    companion object {
        const val DOES_NOT_REPEAT = "does_not_repeat"
        const val DAILY = "daily"
        const val WEEKLY = "weekly"
        const val MONTHLY = "monthly"

        fun from(s: String) =
            when (s) {
                DOES_NOT_REPEAT -> DoesNotRepeat
                DAILY -> Daily
                WEEKLY -> Weekly
                MONTHLY -> Monthly
                else -> DoesNotRepeat
            }
    }
}
