package com.hellguy39.hellnotes.navigation.graph

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.activity.main.MainActivity
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.navigation.host.MainNavHost
import dagger.hilt.android.EntryPointAccessors

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavGraph(
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.Main.route
    ) {
        MainNavHost(
            globalNavController = globalNavController
        )
    }
}

//@Composable
//fun noteDetailViewModel(noteId: Long): NoteDetailViewModel {
//    val factory = EntryPointAccessors.fromActivity(
//        LocalContext.current as Activity,
//        MainActivity.ViewModelFactoryProvider::class.java
//    ).noteDetailsViewModelFactory()
//    return viewModel(factory = NoteDetailViewModel.provideFactory(factory, noteId))
//}

@Composable
fun mainViewModel(navController: NavController): MainViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).mainViewModelFactory()
    return viewModel(factory = MainViewModel.provideFactory(factory, navController))
}