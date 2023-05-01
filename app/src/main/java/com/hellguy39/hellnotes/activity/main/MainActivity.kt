package com.hellguy39.hellnotes.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.hellguy39.hellnotes.android_features.AndroidAlarmScheduler
import com.hellguy39.hellnotes.core.model.OnStartupArguments
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.system.TransparentSystemBars
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        analytics = Firebase.analytics
        setContent { App() }
    }

    @Composable
    fun App() {
        HellNotesTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                TransparentSystemBars()
                SetupNavGraph(args = intent.getOnStartupArgs())
            }
        }
    }

    private fun Intent.getOnStartupArgs(): OnStartupArguments = OnStartupArguments(
        extraNoteId = this.extras?.getLong(AndroidAlarmScheduler.ALARM_NOTE_ID, ArgumentDefaultValues.Empty) ?: ArgumentDefaultValues.Empty,
        action = this.extras?.getString(ArgumentKeys.ShortcutAction, "") ?: ""
    )

}