package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GlobalNavGraph() {
    val globalNavController = rememberAnimatedNavController()

    AnimatedNavHost(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            },
        navController = globalNavController,
        startDestination = GraphScreen.Global.Main.route
    ) {

        mainNavGraph(globalNavController)

        settingsNavGraph(globalNavController)

        aboutNavGraph(globalNavController)

    }
}