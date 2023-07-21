package com.hellguy39.hellnotes.core.ui.model

import android.graphics.Rect
import androidx.window.layout.FoldingFeature

sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ) : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture
}