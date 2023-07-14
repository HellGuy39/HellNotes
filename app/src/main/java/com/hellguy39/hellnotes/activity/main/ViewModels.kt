package com.hellguy39.hellnotes.activity.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.feature.about_app.AboutViewModel
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun mainViewModel(navController: NavController): MainViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).mainViewModelFactory()
    return viewModel(factory = MainViewModel.provideFactory(factory, navController))
}

@Composable
fun settingsViewModel(navController: NavController): SettingsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).settingsViewModelFactory()
    return viewModel(factory = SettingsViewModel.provideFactory(factory, navController))
}

@Composable
fun aboutViewModel(navController: NavController): AboutViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).aboutViewModelFactory()
    return viewModel(factory = AboutViewModel.provideFactory(factory, navController))
}