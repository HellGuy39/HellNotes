package com.hellguy39.hellnotes.core.model.wrapper

data class Nameable<T>(
    val name: String,
    val value: T
)
