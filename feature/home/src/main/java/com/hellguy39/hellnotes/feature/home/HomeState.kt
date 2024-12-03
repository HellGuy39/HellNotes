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
package com.hellguy39.hellnotes.feature.home

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hellguy39.hellnotes.core.ui.components.snack.showSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.state.findStartDestination
import com.hellguy39.hellnotes.core.ui.state.resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberHomeState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = remember(navController, resources, coroutineScope, drawerState, snackbarHostState) {
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
            dismissSnack()
            closeDrawer()
            navController.navigate(route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
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
        snackbarHostState.showSnackbar(
            scope = coroutineScope,
            message = text.asString(resources),
            actionLabel = if (withUndo) resources.getString(AppStrings.Button.Undo) else null,
            duration = SnackbarDuration.Long,
            onActionPerformed = onActionPerformed,
        )
    }

    fun dismissSnack() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }
}
