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
package com.hellguy39.hellnotes.feature.aboutapp.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.navigation.Graph
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.defaultNavOptions
import com.hellguy39.hellnotes.core.ui.navigations.navigateTo
import com.hellguy39.hellnotes.core.ui.state.GraphState
import com.hellguy39.hellnotes.core.ui.state.rememberGraphState
import com.hellguy39.hellnotes.feature.aboutapp.screen.aboutapp.AboutAppScreen
import com.hellguy39.hellnotes.feature.aboutapp.screen.aboutapp.aboutAppScreen
import com.hellguy39.hellnotes.feature.aboutapp.screen.changelog.changelogScreen
import com.hellguy39.hellnotes.feature.aboutapp.screen.privacypolicy.privacyPolicyScreen
import com.hellguy39.hellnotes.feature.aboutapp.screen.reset.resetScreen
import com.hellguy39.hellnotes.feature.aboutapp.screen.termsandconditions.termsAndConditionsScreen

object AboutAppNavGraph : Graph {
    override val endpoint: String = "about_app"
}

fun GraphState.navigateToAboutAppGraph(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) = navigateTo(from, AboutAppNavGraph.endpoint, navOptions)

@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.aboutNavGraph(exitGraph: () -> Unit) {
    composable(
        route = AboutAppNavGraph.endpoint,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        val graphState = rememberGraphState()

        NavHost(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            navController = graphState.navController,
            startDestination = AboutAppScreen.endpoint,
        ) {
            aboutAppScreen(
                exitGraph = exitGraph,
                aboutAppState = graphState
            )

            changelogScreen(graphState)

            termsAndConditionsScreen(graphState)

            privacyPolicyScreen(graphState)

            resetScreen(graphState)
        }
    }
}
