package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.feature.home.util.DrawerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
    private val appSettingsRepository: AppSettingsRepository,
    val dateHelper: DateHelper
): ViewModel() {

    val labels = labelRepository.getAllLabelsStream()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            listOf()
        )

    private val _listStyle = MutableStateFlow(appSettingsRepository.getListStyle())
    val listStyle = _listStyle.asStateFlow()

    private val _drawerItem = MutableStateFlow(DrawerItem())
    val drawerItem = _drawerItem.asStateFlow()

    fun updateListStyle() = viewModelScope.launch {
        val newStyle = _listStyle.value.swap()
        appSettingsRepository.saveListStyle(newStyle)
        _listStyle.update { newStyle }
    }

    fun setDrawerItem(drawerItem: DrawerItem) {
        viewModelScope.launch {
            _drawerItem.update { drawerItem }
        }
    }

}

