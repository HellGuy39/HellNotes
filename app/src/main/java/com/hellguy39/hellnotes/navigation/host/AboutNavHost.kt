package com.hellguy39.hellnotes.navigation.host

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.feature.about_app.AboutViewModel

@Composable
fun AboutNavHost(
    displayFeatures: List<DisplayFeature>,
    windowSize: WindowSizeClass,
    aboutViewModel: AboutViewModel = hiltViewModel()
) {

}