package com.hellguy39.hellnotes.activity.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.model.local.datastore.ThemeState
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.navigation.graph.GlobalNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent { App() }
    }

    @Composable
    fun App() {
        val appearanceViewModel: AppearanceViewModel = hiltViewModel()
        val appearanceState by appearanceViewModel.appearanceState.collectAsStateWithLifecycle()

        val displayFeatures = calculateDisplayFeatures(this)

        HellNotesTheme(
            darkTheme = when(appearanceState.theme) {
                is ThemeState.System -> isSystemInDarkTheme()
                is ThemeState.Dark -> true
                is ThemeState.Light -> false
            },
            dynamicColor = appearanceState.isMaterialYouEnabled
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                //TransparentSystemBars()
                GlobalNavGraph(displayFeatures)
            }
        }
    }

//    private fun Intent.getOnStartupArgs() = OnStartupArguments(
//        extraNoteId = this.extras?.getLong(AndroidAlarmScheduler.ALARM_NOTE_ID, ArgumentDefaultValues.Empty) ?: ArgumentDefaultValues.Empty,
//        action = this.extras?.getString(ArgumentKeys.ShortcutAction, "") ?: ""
//    )

}