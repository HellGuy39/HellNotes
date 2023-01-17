package com.hellguy39.hellnotes.core.model.util

sealed interface ListStyle {
    object Column: ListStyle
    object Grid: ListStyle

    fun parse(): String {
        return when(this) {
            is Grid -> GRID
            is Column -> COLUMN
        }
    }

    companion object {

        const val GRID = "list_style_grid"
        const val COLUMN = "list_style_column"

        fun from(s: String): ListStyle {
            return when(s) {
                GRID -> Grid
                COLUMN -> Column
                else -> Column
            }
        }
    }
}