package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.ui.WindowInfo
import com.hellguy39.hellnotes.core.ui.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.navigations.DevicePosture
import com.hellguy39.hellnotes.core.ui.navigations.HNContentType
import com.hellguy39.hellnotes.core.ui.navigations.HNNavigationContentPosition
import com.hellguy39.hellnotes.core.ui.navigations.HNNavigationType
import com.hellguy39.hellnotes.core.ui.navigations.isBookPosture
import com.hellguy39.hellnotes.core.ui.navigations.isSeparating
import com.hellguy39.hellnotes.core.ui.rememberWindowInfo
import com.hellguy39.hellnotes.feature.home.MainRoute
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.util.getHomeNavigationItems
import com.hellguy39.hellnotes.navigation.graph.mainViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    globalNavController: NavController,
    mainViewModel: MainViewModel = mainViewModel(navController = globalNavController)
) {
    val context = LocalContext.current
    val mainNavController = rememberAnimatedNavController()
    val windowInfo = rememberWindowInfo()
    val displayFeatures = calculateDisplayFeatures(activity = context as Activity)

    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: HNNavigationType
    val contentType: HNContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> {
            navigationType = HNNavigationType.BottomNavigation
            contentType = HNContentType.SinglePane
        }
        is WindowInfo.WindowType.Medium -> {
            navigationType = HNNavigationType.NavigationRail
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                HNContentType.DualPane
            } else {
                HNContentType.SinglePane
            }
        }
        is WindowInfo.WindowType.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                HNNavigationType.NavigationRail
            } else {
                HNNavigationType.PermanentNavigationDrawer
            }
            contentType = HNContentType.DualPane
        }
        else -> {
            navigationType = HNNavigationType.BottomNavigation
            contentType = HNContentType.SinglePane
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowInfo.screenHeightInfo) {
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

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = getHomeNavigationItems(
        onItemClick = { item ->
            mainNavController.navigate(item.screen.route) {
                popUpTo(mainNavController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            MainRoute(
                innerPadding = innerPadding,
                mainNavController = mainNavController,
                mainViewModel = mainViewModel,
                contentType = contentType,
                displayFeatures = displayFeatures,
                onCloseNoteEdit = mainViewModel::closeNoteEdit
            )
        }
    }

    when(navigationType) {
        is HNNavigationType.BottomNavigation -> {
            BottomNavigationBarLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                isVisible = true,
                content = { _ -> navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote
            )
        }
        is HNNavigationType.NavigationRail -> {
            NavigationRailLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote,
            )
        }
        is HNNavigationType.PermanentNavigationDrawer -> {
            NavigationDrawerLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote,
            )
        }
    }
}