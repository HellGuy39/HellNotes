package com.hellguy39.hellnotes.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.activity.main.aboutViewModel
import com.hellguy39.hellnotes.feature.about_app.AboutViewModel

@Composable
fun AboutNavHost(
    globalNavController: NavController,
    aboutViewModel: AboutViewModel = aboutViewModel(navController = globalNavController)
) {

}