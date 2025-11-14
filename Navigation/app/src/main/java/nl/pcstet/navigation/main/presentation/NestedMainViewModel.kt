package nl.pcstet.navigation.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore

class NestedMainViewModel(
    val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {
    val onboardingState: StateFlow<OnboardingState> = userPreferencesDataStore.backendUri
        .map { value ->
            when (value) {
                null -> OnboardingState.OnboardingRequired
                else -> OnboardingState.OnboardingComplete(value)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = OnboardingState.Loading
        )

    init {
        viewModelScope.launch {
            onboardingState.collect { value ->
                Log.d("MainViewModel", "$value")
            }
        }
    }
}

sealed interface OnboardingState {
    data object Loading : OnboardingState
    data object OnboardingRequired : OnboardingState
    data class OnboardingComplete(val backendUrl: String) : OnboardingState
}