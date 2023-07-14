package com.hellguy39.hellnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*

//@HiltViewModel
//class SettingsViewModel @Inject constructor(
//    private val dataStoreRepository: DataStoreRepository,
//    val biometricAuth: BiometricAuthenticator,
//    private val languageHolder: LanguageHolder
//): ViewModel() {
//
//    private val isBiometricAuthAvailable =
//        biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success
//
//    private val languageCode = MutableStateFlow(languageHolder.getLanguageCode())
//
//    val uiState = combine(
//        dataStoreRepository.readSecurityState(),
//        dataStoreRepository.readNoteStyleState(),
//        dataStoreRepository.readNoteSwipesState(),
//        dataStoreRepository.readLastBackupDate(),
//        languageCode,
//    ) { securityState, noteStyle, noteSwipesState, lastBackupDate, languageCode ->
//        SettingsUiState(
//            securityState = securityState,
//            noteStyle = noteStyle,
//            noteSwipesState = noteSwipesState,
//            isBioAuthAvailable = isBiometricAuthAvailable,
//            lanCode = languageCode,
//            lastBackupDate = lastBackupDate
//        )
//    }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = SettingsUiState.initialInstance()
//        )
//
//
//    fun send(uiEvent: SettingsUiEvent) {
//        when(uiEvent) {
//            is SettingsUiEvent.Start -> {
//                languageCode.update { languageHolder.getLanguageCode() }
//            }
//            is SettingsUiEvent.ToggleIsUseBiometricData -> {
//               saveIsUseBiometricData(uiEvent.isUseBiometric)
//            }
//        }
//    }
//
//    private fun saveIsUseBiometricData(isUseBiometric: Boolean) {
//        viewModelScope.launch {
//            val state = uiState.value.securityState
//            dataStoreRepository.saveSecurityState(state.copy(isUseBiometricData = isUseBiometric))
//        }
//    }
//
//}
//
//sealed class SettingsUiEvent {
//
//    object Start: SettingsUiEvent()
//
//    data class ToggleIsUseBiometricData(val isUseBiometric: Boolean): SettingsUiEvent()
//
//}
//
//data class SettingsUiState(
//    val securityState: SecurityState,
//    val lanCode: String,
//    val lastBackupDate: Long,
//    val isBioAuthAvailable: Boolean,
//    val noteStyle: NoteStyle,
//    val noteSwipesState: NoteSwipesState
//) {
//    companion object {
//        fun initialInstance() = SettingsUiState(
//            securityState = SecurityState.initialInstance(),
//            lanCode = Language.SystemDefault.code,
//            isBioAuthAvailable = false,
//            noteStyle = NoteStyle.Outlined,
//            noteSwipesState = NoteSwipesState.initialInstance(),
//            lastBackupDate = 0L
//        )
//    }
//}

class SettingsViewModel @AssistedInject constructor(
    @Assisted private val globalNavController: NavController,
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(navController: NavController): SettingsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            navController: NavController
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(navController) as T
            }
        }
    }
}