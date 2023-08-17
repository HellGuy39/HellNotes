package com.hellguy39.hellnotes.navigation.host

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.hellguy39.hellnotes.feature.about_app.AboutViewModel

@Composable
fun AboutNavHost(
    aboutViewModel: AboutViewModel = hiltViewModel()
) {

}