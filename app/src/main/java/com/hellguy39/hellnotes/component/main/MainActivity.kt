package com.hellguy39.hellnotes.component.main

import android.app.Activity
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
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.model.OnStartupArguments
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.rememberHellNotesAppState
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.navigation.GlobalNavGraph
import com.hellguy39.hellnotes.tools.AlarmSchedulerImpl
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
        setContent { HellNotesApp(args = intent.getOnStartupArgs()) }
    }

    private fun Intent.getOnStartupArgs(): OnStartupArguments =
        OnStartupArguments(
            extraNoteId =
                this.extras?.getLong(
                    AlarmSchedulerImpl.ALARM_NOTE_ID, ArgumentDefaultValues.EMPTY,
                ) ?: ArgumentDefaultValues.EMPTY,
            action = this.extras?.getString(ArgumentKeys.SHORTCUT_ACTION, "") ?: "",
        )
}

@Composable
fun HellNotesApp(args: OnStartupArguments) {
    val appState = rememberHellNotesAppState()
    HellNotesTheme {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .imePadding(),
            color = MaterialTheme.colorScheme.background,
        ) {
            GlobalNavGraph(
                appState = appState,
                args = args,
            )
        }
    }
}

fun Activity.setTransparentSystemBars(isTransparent: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, isTransparent.not())
}
