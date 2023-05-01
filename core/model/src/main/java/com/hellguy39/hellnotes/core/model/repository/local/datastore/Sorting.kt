package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed interface Sorting {
    object DateOfCreation: Sorting
    object DateOfLastEdit: Sorting

    fun string(): String {
        return when(this) {
            is DateOfLastEdit -> DATE_OF_LAST_EDIT
            is DateOfCreation -> DATE_OF_CREATION
        }
    }

    companion object {

        private const val DATE_OF_CREATION = "date_of_creation"
        private const val DATE_OF_LAST_EDIT = "date_of_last_edit"

        fun from(
            s: String?,
            defaultValue: Sorting = DateOfCreation
        ) = when(s) {
            DATE_OF_LAST_EDIT -> DateOfLastEdit
            DATE_OF_CREATION -> DateOfCreation
            else -> defaultValue
        }
    }

}