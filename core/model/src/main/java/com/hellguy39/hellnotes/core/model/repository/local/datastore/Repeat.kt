package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed interface Repeat {
    object DoesNotRepeat: Repeat
    object Daily: Repeat
    object Weekly: Repeat
    object Monthly: Repeat

    fun string(): String {
        return when(this) {
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

        fun from(s: String) = when(s) {
            DOES_NOT_REPEAT -> DoesNotRepeat
            DAILY -> Daily
            WEEKLY -> Weekly
            MONTHLY -> Monthly
            else -> DoesNotRepeat
        }
    }

}
