package com.hellguy39.hellnotes.core.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.navigations.DevicePosture
import com.hellguy39.hellnotes.core.ui.navigations.HNContentType
import com.hellguy39.hellnotes.core.ui.navigations.HNNavigationContentPosition
import com.hellguy39.hellnotes.core.ui.navigations.HNNavigationType
import com.hellguy39.hellnotes.core.ui.navigations.isBookPosture
import com.hellguy39.hellnotes.core.ui.navigations.isSeparating

@Composable
fun rememberWindowInfo(): WindowInfo = calculateWindowInfo()

@Composable
fun rememberFoldingDevicePosture(): DevicePosture = calculateDevicePosture()

@Composable
fun rememberNavigationType(): HNNavigationType {

    val devicePosture = calculateDevicePosture()
    val windowInfo = calculateWindowInfo()

    return when (windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> HNNavigationType.BottomNavigation

        is WindowInfo.WindowType.Medium -> HNNavigationType.NavigationRail
        is WindowInfo.WindowType.Expanded -> if (devicePosture is DevicePosture.BookPosture) {
            HNNavigationType.NavigationRail
        } else {
            HNNavigationType.PermanentNavigationDrawer
        }
    }
}

@Composable
fun rememberNavigationContentPosition(): HNNavigationContentPosition {

    val windowInfo = calculateWindowInfo()

    return when (windowInfo.screenHeightInfo) {
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

@Composable
fun rememberContentType(): HNContentType {

    val windowInfo = calculateWindowInfo()
    val devicePosture = calculateDevicePosture()

    return when (windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> HNContentType.SinglePane
        is WindowInfo.WindowType.Medium -> if (devicePosture != DevicePosture.NormalPosture) {
            HNContentType.DualPane
        } else {
            HNContentType.SinglePane
        }

        is WindowInfo.WindowType.Expanded -> HNContentType.DualPane
        else -> HNContentType.SinglePane
    }
}

@Composable
private fun calculateDevicePosture(): DevicePosture {
    val displayFeatures = calculateDisplayFeatures(activity = LocalContext.current as Activity)

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    return when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }
}

@Composable
private fun calculateWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
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