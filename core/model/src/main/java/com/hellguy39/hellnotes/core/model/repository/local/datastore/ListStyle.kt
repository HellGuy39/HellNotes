package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed class ListStyle(val tag: String) {

    data object Column: ListStyle(COLUMN)
    data object Grid: ListStyle(GRID)

    fun swap(): ListStyle = when(this) {
        Grid -> Column
        Column -> Grid
    }

    companion object {

        const val GRID = "list_style_grid"
        const val COLUMN = "list_style_column"

        fun default() = ListStyle.Column

        fun fromTag(
            tag: String?,
            defaultValue: ListStyle = default()
        ): ListStyle {
            return when(tag) {
                GRID -> Grid
                COLUMN -> Column
                else -> defaultValue
            }
        }
    }
}