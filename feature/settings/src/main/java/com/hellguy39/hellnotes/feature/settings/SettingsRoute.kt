package com.hellguy39.hellnotes.feature.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hellguy39.hellnotes.core.ui.layout.ListDetail
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen
import com.hellguy39.hellnotes.core.ui.navigations.HNContentType

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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SettingsRoute(
//    innerPadding: PaddingValues,
//    mainNavController: NavHostController,
//    contentType: HNContentType,
//    displayFeatures: List<DisplayFeature>,
) {
//    ListDetail(
//        modifier = Modifier,
//        isDetailOpen = true,
//        onCloseDetail = {},
//        contentType = contentType,
//        detailKey = -1,
//        list = { isDetailVisible ->
//            NoteEditRoute(
//                contentType = contentType,
//                noteId = openedNoteId,
//                onCloseNoteEdit = onCloseNoteEdit
//            )
//        },
//        detail = { isListVisible ->
//            AnimatedNavHost(
//                modifier = Modifier.padding(innerPadding),
//                navController = mainNavController,
//                startDestination = GraphScreen.Main.start().route
//            ) {
//                notesScreen(mainViewModel)
//
//                remindersScreen(mainViewModel)
//
//                archiveScreen(mainViewModel)
//
//                labelsScreen(mainViewModel)
//
//                trashScreen(mainViewModel)
//            }
//        },
//        twoPaneStrategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
//        displayFeatures = displayFeatures
//    )
}
