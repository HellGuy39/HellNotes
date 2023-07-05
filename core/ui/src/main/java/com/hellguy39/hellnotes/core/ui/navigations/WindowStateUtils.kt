package com.hellguy39.hellnotes.core.ui.navigations

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import androidx.compose.runtime.saveable.Saver

/**
 * Information about the posture of the device
 */
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

sealed class HNNavigationType {

    object BottomNavigation : HNNavigationType()

    object NavigationRail : HNNavigationType()

    object PermanentNavigationDrawer : HNNavigationType()

    override fun toString(): String = when (this) {
        is BottomNavigation -> BOTTOM_NAVIGATION
        is NavigationRail -> NAVIGATION_RAIL
        is PermanentNavigationDrawer -> PERMANENT_NAVIGATION_DRAWER
    }

    companion object {

        const val BOTTOM_NAVIGATION = "BottomNavigation"
        const val NAVIGATION_RAIL = "NavigationRail"
        const val PERMANENT_NAVIGATION_DRAWER = "PermanentNavigationDrawer"

        fun default() = BottomNavigation

        fun from(s: String): HNNavigationType = when (s) {
            BOTTOM_NAVIGATION -> BottomNavigation
            NAVIGATION_RAIL -> NavigationRail
            PERMANENT_NAVIGATION_DRAWER -> PermanentNavigationDrawer
            else -> default()
        }

        fun Saver() = Saver<HNNavigationType, String>(
            save = { navigationType -> navigationType.toString() },
            restore = { s -> from(s) }
        )
    }
}

sealed class HNContentType {

    object SinglePane : HNContentType()

    object DualPane : HNContentType()
}

sealed class HNNavigationContentPosition {

    object Top : HNNavigationContentPosition()

    object Center : HNNavigationContentPosition()

}
