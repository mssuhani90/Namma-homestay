package com.example.nammahomestay.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammahomestay.data.HomestayProfile
import com.example.nammahomestay.data.HomestayRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data object Idle : HomeUiState
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Loaded(
        val profile: HomestayProfile? = null,
        val images: List<Uri> = emptyList(),
        val menuText: String = ""
    ) : HomeUiState
}

class HomeViewModel(
    private val repo: HomestayRepository = HomestayRepository(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _menuText = MutableStateFlow("")
    val menuText: StateFlow<String> = _menuText.asStateFlow()

    fun load() {
        _state.value = HomeUiState.Loading
        viewModelScope.launch {
            val result = repo.loadProfile()
            _state.value = result.fold(
                onSuccess = { loaded ->
                    HomeUiState.Loaded(
                        profile = loaded,
                        images = emptyList(),
                        menuText = loaded?.menu ?: ""
                    )
                },
                onFailure = { e -> HomeUiState.Error(e.message ?: "Unknown error") }
            )
        }
    }

    fun saveMenu(menu: String) {
        _state.value = HomeUiState.Loading
        viewModelScope.launch {
            val result = repo.saveMenu(menu)
            _state.value = result.fold(
                onSuccess = { HomeUiState.Loaded(profile = null, images = emptyList(), menuText = menu) },
                onFailure = { e -> HomeUiState.Error(e.message ?: "Unknown error") }
            )
            _menuText.value = menu
        }
    }

    fun saveProfile(profile: HomestayProfile) {
        _state.value = HomeUiState.Loading
        viewModelScope.launch {
            val result = repo.saveProfile(profile)
            _state.value = result.fold(
                onSuccess = { HomeUiState.Loaded(profile = profile, images = emptyList(), menuText = profile.menu) },
                onFailure = { e -> HomeUiState.Error(e.message ?: "Unknown error") }
            )
        }
    }
}

