package com.hellguy39.hellnotes.core.ui.window

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.hellguy39.hellnotes.core.ui.model.DevicePosture
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.model.HNNavigationContentPosition
import com.hellguy39.hellnotes.core.ui.model.HNNavigationType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Composable
fun rememberFoldingDevicePosture(displayFeatures: List<DisplayFeature>): DevicePosture {
    return remember { calculateDevicePosture(displayFeatures) }
}

@Composable
fun rememberNavigationType(
    displayFeatures: List<DisplayFeature>,
    windowWidthSize: WindowWidthSizeClass,
): HNNavigationType {
    return remember { calculateNavigationType(displayFeatures, windowWidthSize) }
}

@Composable
fun rememberNavigationContentPosition(
    windowHeightSize: WindowHeightSizeClass
): HNNavigationContentPosition {
    return remember { calculateNavigationContentPosition(windowHeightSize) }
}

@Composable
fun rememberContentType(
    displayFeatures: List<DisplayFeature>,
    windowWidthSize: WindowWidthSizeClass
): HNContentType {
    return remember { calculateContentType(displayFeatures, windowWidthSize) }
}

fun calculateNavigationContentPosition(
    windowHeightSize: WindowHeightSizeClass
): HNNavigationContentPosition {
    return when (windowHeightSize) {
        WindowHeightSizeClass.Compact -> HNNavigationContentPosition.Top
        WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> HNNavigationContentPosition.Center
        else -> HNNavigationContentPosition.Top
    }
}

fun calculateNavigationType(
    displayFeatures: List<DisplayFeature>,
    windowWidthSize: WindowWidthSizeClass,
): HNNavigationType {
    val devicePosture = calculateDevicePosture(displayFeatures)

    return when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> HNNavigationType.BottomNavigation

        WindowWidthSizeClass.Medium -> HNNavigationType.NavigationRail
        WindowWidthSizeClass.Expanded -> if (devicePosture is DevicePosture.BookPosture) {
            HNNavigationType.NavigationRail
        } else {
            HNNavigationType.PermanentNavigationDrawer
        }

        else -> HNNavigationType.BottomNavigation
    }
}

fun calculateContentType(
    displayFeatures: List<DisplayFeature>,
    windowWidthSize: WindowWidthSizeClass
): HNContentType {
    val devicePosture = calculateDevicePosture(displayFeatures)

    return when (windowWidthSize) {
        WindowWidthSizeClass.Compact -> HNContentType.SinglePane
        WindowWidthSizeClass.Medium -> when (devicePosture) {
            DevicePosture.NormalPosture -> HNContentType.SinglePane
            else -> HNContentType.DualPane
        }

        WindowWidthSizeClass.Expanded -> HNContentType.DualPane
        else -> HNContentType.SinglePane
    }
}

private fun calculateDevicePosture(displayFeatures: List<DisplayFeature>): DevicePosture {

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    return when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

fun WindowWidthSizeClass.isCompact(): Boolean {
    return this == WindowWidthSizeClass.Compact
}

fun WindowWidthSizeClass.isNotCompact(): Boolean {
    return this != WindowWidthSizeClass.Compact
}

fun WindowWidthSizeClass.isMedium(): Boolean {
    return this == WindowWidthSizeClass.Compact
}

fun WindowWidthSizeClass.isNotMedium(): Boolean {
    return this != WindowWidthSizeClass.Compact
}

fun WindowWidthSizeClass.isExpanded(): Boolean {
    return this == WindowWidthSizeClass.Compact
}

fun WindowWidthSizeClass.isNotExpanded(): Boolean {
    return this != WindowWidthSizeClass.Compact
}

fun WindowHeightSizeClass.isCompact(): Boolean {
    return this == WindowHeightSizeClass.Compact
}

fun WindowHeightSizeClass.isNotCompact(): Boolean {
    return this != WindowHeightSizeClass.Compact
}

fun WindowHeightSizeClass.isMedium(): Boolean {
    return this == WindowHeightSizeClass.Compact
}

fun WindowHeightSizeClass.isNotMedium(): Boolean {
    return this != WindowHeightSizeClass.Compact
}

fun WindowHeightSizeClass.isExpanded(): Boolean {
    return this == WindowHeightSizeClass.Compact
}

fun WindowHeightSizeClass.isNotExpanded(): Boolean {
    return this != WindowHeightSizeClass.Compact
}