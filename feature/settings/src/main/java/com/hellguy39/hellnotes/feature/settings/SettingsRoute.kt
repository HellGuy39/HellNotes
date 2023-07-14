package com.hellguy39.hellnotes.feature.settings

import androidx.compose.runtime.Composable

//@Composable
//fun SettingsRoute(
//    navController: NavController,
//    settingsViewModel: SettingsViewModel = hiltViewModel()
//) {
//    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
//
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val context = LocalContext.current
//
//    val currentOnStart by rememberUpdatedState {
//        settingsViewModel.send(SettingsUiEvent.Start)
//    }
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            when(event) {
//                Lifecycle.Event.ON_START -> currentOnStart()
//                else -> Unit
//            }
//        }
//
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }
//
//    SettingsScreen(
//        onNavigationButtonClick = navController::popBackStack,
//        uiState = uiState,
//        selection = SettingsScreenSelection(
//            onLanguage = {
//                navController.navigateToLanguageSelection()
//            },
//            onUseBiometric = { isUseBiometricData ->
//                if (isUseBiometricData) {
//                    if (settingsViewModel.biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success) {
//                        settingsViewModel.biometricAuth.setOnAuthListener { result ->
//                            if (result == AuthenticationResult.Success) {
//                                settingsViewModel.send(SettingsUiEvent.ToggleIsUseBiometricData(isUseBiometricData))
//                            }
//                        }
//                        settingsViewModel.biometricAuth.authenticate(context as AppCompatActivity)
//                    }
//                } else {
//                    settingsViewModel.send(SettingsUiEvent.ToggleIsUseBiometricData(isUseBiometricData))
//                }
//            },
//            onLockScreen = {
//                navController.navigateToLockSelection()
//            },
//            onNoteStyleEdit = {
//                navController.navigateToNoteStyleEdit()
//            },
//            onNoteSwipeEdit = {
//                navController.navigateToNoteSwipeEdit()
//            },
//            onBackup = {
//                navController.navigateToBackup()
//            },
//            onMaterialYouEnabled = {
//
//            },
//            onThemeToggle = {
//
//            }
//        )
//    )
//}

@Composable
fun SettingsRoute(

) {

}
