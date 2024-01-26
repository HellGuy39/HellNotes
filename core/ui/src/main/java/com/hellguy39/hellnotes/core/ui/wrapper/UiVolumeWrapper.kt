package com.hellguy39.hellnotes.core.ui.wrapper

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText

data class UiVolume<T>(
    val partitions: SnapshotStateList<UiPartition<T>>,
) {
    val isSinglePartition: Boolean
        get() {
            var nonEmptyPartitions = 0
            for (partition in partitions) {
                if (!partition.isEmpty) {
                    nonEmptyPartitions++
                    if (nonEmptyPartitions >= 2) {
                        break
                    }
                }
            }
            return nonEmptyPartitions <= 1
        }

    fun getElementByPositionInfo(positionInfo: PartitionElementPositionInfo): T {
        return partitions[positionInfo.partitionIndex].elements[positionInfo.elementIndex]
    }
}

data class UiPartition<T>(
    val name: UiText,
    val elements: SnapshotStateList<T>,
) {
    val isEmpty: Boolean
        get() = elements.isEmpty()
}

data class PartitionElementPositionInfo(
    val partitionIndex: Int,
    val elementIndex: Int,
)
