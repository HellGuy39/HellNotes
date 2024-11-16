/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    val isEmpty: Boolean
        get() {
            for (partition in partitions) {
                if (!partition.isEmpty) {
                    return false
                }
            }
            return true
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
