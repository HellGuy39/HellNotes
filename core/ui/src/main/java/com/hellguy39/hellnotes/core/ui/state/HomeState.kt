package com.hellguy39.hellnotes.core.ui.state

import android.content.res.Resources
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberHomeState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = remember(navController, resources, coroutineScope) {
    HomeState(
        navController = navController,
        resources = resources,
        coroutineScope = coroutineScope,
        drawerState = drawerState,
        snackbarHostState = snackbarHostState,
    )
}

@Stable
class HomeState(
    val navController: NavHostController,
    val resources: Resources,
    val coroutineScope: CoroutineScope,
    val drawerState: DrawerState,
    val snackbarHostState: SnackbarHostState,
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateUp() {
        navController.navigateUp()
    }

    fun openDrawer() {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    fun closeDrawer() {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    fun navigateToNavigationBarRoute(route: String) {
        if (route != currentRoute) {
            closeDrawer()
            navController.navigate(route) {
                // TODO: This breaks deeplinks
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun resetNavigation() {
        val startRoute = findStartDestination(navController.graph).route
        if (startRoute != null) {
            navigateToNavigationBarRoute(startRoute)
        }
    }

    fun showSnack(
        text: UiText,
        onActionPerformed: () -> Unit,
        withUndo: Boolean = true,
    ) {
        snackbarHostState.showDismissableSnackbar(
            scope = coroutineScope,
            message = text.asString(resources),
            actionLabel = if (withUndo) resources.getString(AppStrings.Button.Undo) else null,
            duration = SnackbarDuration.Long,
            onActionPerformed = onActionPerformed,
        )
    }
}
