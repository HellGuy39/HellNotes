package com.hellguy39.hellnotes.activity.crash

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.system.TransparentSystemBars
import com.hellguy39.hellnotes.navigation.SetupNavGraph
import com.hellguy39.hellnotes.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.util.GlobalExceptionHandler

class CrashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HellNotesTheme {

                val error = GlobalExceptionHandler.getThrowableFromIntent(intent)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn {
                        item {
                            Text(
                                text = "$error"
                            )
                        }
                    }
                }
            }
        }
    }

}