package com.hellguy39.hellnotes.core.model.wrapper

data class Selectable<T>(
    val value: T,
    val selected: Boolean
)
