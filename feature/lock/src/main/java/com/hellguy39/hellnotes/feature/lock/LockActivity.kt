package com.hellguy39.hellnotes.feature.lock

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.ui.system.TransparentSystemBars
import com.hellguy39.hellnotes.ui.theme.HellNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LockActivity : AppCompatActivity() {

    private val bioNoneEnrolledLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }

    @Inject lateinit var biometricAuth: BiometricAuthenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        onBackPressedDispatcher.addCallback {  }

        setContent {
            HellNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransparentSystemBars()
                    LockRoute(
                        onUnlock = {
                            finish()
                        }
                    )
                }

//                biometricAuth.setOnAuthListener { result ->
//                    when (result) {
//                        is AuthenticationResult.Success -> {
//                            lockViewModel.authByBiometric()
//                            navigateToMainActivity()
//                        }
//                        is AuthenticationResult.Failed -> Unit
//                        is AuthenticationResult.Error -> Unit
//                    }
//                }
//
//                if (lockViewModel.isUseBiometric()) {
//                    authByBiometric()
//                }
            }
        }
    }

}