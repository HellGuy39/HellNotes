package com.hellguy39.hellnotes.core.ui.window

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.model.DevicePosture
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.model.HNNavigationContentPosition
import com.hellguy39.hellnotes.core.ui.model.HNNavigationType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return remember { calculateWindowInfo(configuration) }
}

@Composable
fun rememberFoldingDevicePosture(): DevicePosture {
    val displayFeatures = calculateDisplayFeatures(activity = LocalContext.current as Activity)
    return remember { calculateDevicePosture(displayFeatures) }
}

@Composable
fun rememberNavigationType(): HNNavigationType {

    val configuration = LocalConfiguration.current
    val displayFeatures = calculateDisplayFeatures(activity = LocalContext.current as Activity)

    val devicePosture = calculateDevicePosture(displayFeatures)
    val windowInfo = calculateWindowInfo(configuration)

    return remember {
        when (windowInfo.screenWidthInfo) {
            is WindowInfo.WindowType.Compact -> HNNavigationType.BottomNavigation

            is WindowInfo.WindowType.Medium -> HNNavigationType.NavigationRail
            is WindowInfo.WindowType.Expanded -> if (devicePosture is DevicePosture.BookPosture) {
                HNNavigationType.NavigationRail
            } else {
                HNNavigationType.PermanentNavigationDrawer
            }
        }
    }
}

@Composable
fun rememberNavigationContentPosition(): HNNavigationContentPosition {

    val configuration = LocalConfiguration.current
    val windowInfo = calculateWindowInfo(configuration)

    return remember {
        when (windowInfo.screenHeightInfo) {
            is WindowInfo.WindowType.Compact -> {
                HNNavigationContentPosition.Top
            }

            is WindowInfo.WindowType.Medium, WindowInfo.WindowType.Expanded -> {
                HNNavigationContentPosition.Center
            }

            else -> {
                HNNavigationContentPosition.Top
            }
        }
    }
}

@Composable
fun rememberContentType(): HNContentType {

    val configuration = LocalConfiguration.current
    val displayFeatures = calculateDisplayFeatures(activity = LocalContext.current as Activity)

    val windowInfo = calculateWindowInfo(configuration)
    val devicePosture = calculateDevicePosture(displayFeatures)

    return remember {
        when (windowInfo.screenWidthInfo) {
            is WindowInfo.WindowType.Compact -> HNContentType.SinglePane
            is WindowInfo.WindowType.Medium -> when (devicePosture) {
                DevicePosture.NormalPosture -> HNContentType.SinglePane
                else -> HNContentType.DualPane
            }
            is WindowInfo.WindowType.Expanded -> HNContentType.DualPane
            else -> HNContentType.SinglePane
        }
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

private fun calculateWindowInfo(configuration: Configuration): WindowInfo {
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}

fun WindowInfo.isExpandedWindowsSize() = when (screenWidthInfo) {
    is WindowInfo.WindowType.Compact -> false
    is WindowInfo.WindowType.Medium -> false
    else -> true
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class WindowType {
        object Compact : WindowType()
        object Medium : WindowType()
        object Expanded : WindowType()
    }
}