package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.feature.home.util.DrawerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    labelRepository: LabelRepository,
    private val dataStoreRepository: DataStoreRepository,
    val dateHelper: DateHelper
): ViewModel() {

    val labels = labelRepository.getAllLabelsStream()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            listOf()
        )

    private val _listStyle: MutableStateFlow<ListStyle> = MutableStateFlow(ListStyle.Column)
    val listStyle = _listStyle.asStateFlow()

    private val _drawerItem = MutableStateFlow(DrawerItem())
    val drawerItem = _drawerItem.asStateFlow()

    private val _appSettings: MutableStateFlow<AppSettings> = MutableStateFlow(AppSettings())
    val appSettings = _appSettings.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                dataStoreRepository.readListStyleState().collect { style ->
                    _listStyle.update { style }
                }
            }
            launch {
                dataStoreRepository.readAppSettings().collect { appSettings ->
                    _appSettings.update { appSettings }
                }
            }
        }
    }

    fun setTrashTipChecked(isChecked: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveTrashTipChecked(isChecked)
        }
    }

    fun updateListStyle() {
        viewModelScope.launch {
            val newStyle = _listStyle.value.swap()
            dataStoreRepository.saveListStyleState(newStyle)
            _listStyle.update { newStyle }
        }
    }

    fun setDrawerItem(drawerItem: DrawerItem) {
        viewModelScope.launch {
            _drawerItem.update { drawerItem }
        }
    }

}

