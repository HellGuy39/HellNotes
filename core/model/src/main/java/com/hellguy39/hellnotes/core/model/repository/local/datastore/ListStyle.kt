package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed interface ListStyle {
    object Column: ListStyle
    object Grid: ListStyle

    fun string(): String {
        return when(this) {
            is Grid -> GRID
            is Column -> COLUMN
        }
    }

    fun swap(): ListStyle = when(this) {
        Grid -> Column
        Column -> Grid
    }

    companion object {

        const val GRID = "list_style_grid"
        const val COLUMN = "list_style_column"

        fun from(
            s: String?,
            defaultValue: ListStyle = Column
        ): ListStyle {
            return when(s) {
                GRID -> Grid
                COLUMN -> Column
                else -> defaultValue
            }
        }
    }
}