package com.hellguy39.hellnotes.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.hellguy39.hellnotes.android_features.AndroidAlarmScheduler
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.system.TransparentSystemBars
import com.hellguy39.hellnotes.feature.lock.LockScreenDialog
import com.hellguy39.hellnotes.feature.welcome.WelcomeActivity
import com.hellguy39.hellnotes.navigation.SetupNavGraph
import com.hellguy39.hellnotes.ui.theme.HellNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        setContent { App() }

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(android.R.id.content)
        ) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }

    private fun navigateToLockScreen() {
        val dialog = LockScreenDialog()
        dialog.isCancelable = false
        dialog.setCallback(callback = object: LockScreenDialog.DialogCallback {
            override fun onDismiss() {}
            override fun onSuccess() { dialog.dismiss() }
        })
        dialog.show(supportFragmentManager, "TAG")
    }

//    private fun navigateToWelcomeScreen() {
//        val intent = Intent(this, WelcomeActivity::class.java)
//        startActivity(intent)
//    }

    @Composable
    fun App() {
        HellNotesTheme {

            val extraNoteId = intent.extras?.getLong(AndroidAlarmScheduler.ALARM_NOTE_ID)
            val action = intent.action

            LaunchedEffect(key1 = splashViewModel.isLoading.value) {
                val lockScreenType by splashViewModel.lockScreenType
                val isOnBoardingCompleted by splashViewModel.isOnBoardingCompleted

                if (!isOnBoardingCompleted) {
                    //navigateToWelcomeScreen()
                }

                if (lockScreenType != LockScreenType.None) {
                    navigateToLockScreen()
                }

            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TransparentSystemBars()
                SetupNavGraph(
                    extraNoteId = extraNoteId,
                    action = action,
                )
            }
        }
    }
}