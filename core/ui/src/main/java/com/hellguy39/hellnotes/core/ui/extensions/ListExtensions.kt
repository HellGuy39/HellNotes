package com.hellguy39.hellnotes.core.ui.extensions

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> List<T>.toStateList(): SnapshotStateList<T> {
    return mutableStateListOf<T>().apply {
        addAll(this@toStateList)
    }
}
