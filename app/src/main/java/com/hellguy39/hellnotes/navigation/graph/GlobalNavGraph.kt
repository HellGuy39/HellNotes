package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.model.GraphScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlobalNavGraph(
    displayFeatures: List<DisplayFeature>
) {
    val globalNavController = rememberNavController()

    NavHost(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            },
        navController = globalNavController,
        startDestination = GraphScreen.Global.Main.route
    ) {
        mainNavGraph(
            displayFeatures = displayFeatures,
            globalNavController = globalNavController
        )

        settingsNavGraph(
            displayFeatures = displayFeatures,
            globalNavController = globalNavController
        )

        aboutNavGraph(
            displayFeatures = displayFeatures,
            globalNavController = globalNavController
        )
    }
}