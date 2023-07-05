package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.aboutNavGraph(
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.About.route
    ) {
        //TODO("About Route")
    }
}