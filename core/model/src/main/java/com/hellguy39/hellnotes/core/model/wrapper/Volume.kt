package com.hellguy39.hellnotes.core.model.wrapper

data class Volume<T>(
    val partitions: List<Partition<T>>,
)

data class Partition<T>(
    val name: String,
    val index: Int,
    val data: T
)