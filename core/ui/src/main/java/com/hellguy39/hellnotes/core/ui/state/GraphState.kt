package com.hellguy39.hellnotes.core.ui.state

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberGraphState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(navController, resources, coroutineScope) {
    GraphState(navController, resources, coroutineScope)
}

@Stable
class GraphState(
    val navController: NavHostController,
    private val resources: Resources,
    coroutineScope: CoroutineScope,
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateUp() {
        navController.navigateUp()
    }
}
