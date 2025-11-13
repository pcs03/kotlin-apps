package nl.pcstet.navigation.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore

class MainViewModel(
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : ViewModel() {
    val mainViewModelState: StateFlow<MainViewModelState> = userPreferencesDataStore.backendUri
        .map { value ->
            when (value) {
                null -> MainViewModelState.OnboardingRequired
                else -> MainViewModelState.OnboardingComplete(value)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MainViewModelState.Loading
        )

    init {
        viewModelScope.launch {
            mainViewModelState.collect { value ->
                Log.d("MainViewModel", "$value")
            }
        }
    }
}

sealed interface MainViewModelState {
    data object Loading : MainViewModelState
    data object OnboardingRequired : MainViewModelState
    data class OnboardingComplete(val backendUrl: String) : MainViewModelState
}