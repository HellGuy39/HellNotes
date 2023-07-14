package com.hellguy39.hellnotes.feature.about_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AboutViewModel @AssistedInject constructor(
    @Assisted private val globalNavController: NavController,
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(navController: NavController): AboutViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            navController: NavController
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(navController) as T
            }
        }
    }
}