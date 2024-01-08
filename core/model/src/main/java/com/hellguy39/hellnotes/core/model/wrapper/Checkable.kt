package com.hellguy39.hellnotes.core.model.wrapper

data class Checkable<T>(
    val value: T,
    val checked: Boolean
)
