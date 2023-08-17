package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hellguy39.hellnotes.core.ui.model.GraphScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlobalNavGraph() {

    val globalNavController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize()
            .semantics { testTagsAsResourceId = true },
        navController = globalNavController,
        startDestination = GraphScreen.Global.Main.route
    ) {
        mainNavGraph(globalNavController)

        settingsNavGraph(globalNavController)

        aboutNavGraph(globalNavController)
    }
}