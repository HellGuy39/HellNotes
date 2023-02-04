package com.hellguy39.hellnotes.feature.about_app

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun AboutAppRoute(
    navController: NavController
) {
    val context = LocalContext.current

    AboutAppScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        onEasterEgg = {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            )
            context.startActivity(browserIntent)
        }
    )
}