package com.hellguy39.hellnotes.component.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.ui.analytics.LocalAnalytics
import com.hellguy39.hellnotes.core.ui.state.rememberAppState
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.navigation.GlobalNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var analyticsLogger: AnalyticsLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentSystemBars(true)
        installSplashScreen()
        setContent {
            CompositionLocalProvider(
                LocalAnalytics provides analyticsLogger,
            ) {
                HellNotesApp()
            }
        }
    }
}

@Composable
fun HellNotesApp() {
    val appState = rememberAppState()
    HellNotesTheme {
        Surface(
            modifier =
                Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            GlobalNavGraph(appState)
        }
    }
}

fun Activity.setTransparentSystemBars(isTransparent: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, isTransparent.not())
}
