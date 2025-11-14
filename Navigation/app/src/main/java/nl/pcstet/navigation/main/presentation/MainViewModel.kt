package nl.pcstet.navigation.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.pcstet.navigation.main.data.network.ApiClientHolder
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore

class MainViewModel(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val apiClientHolder: ApiClientHolder,
) : ViewModel() {
    val onboardingState: StateFlow<OnboardingState> = userPreferencesDataStore.backendUrl
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

    fun initializeUnauthenticatedApiClientHolder(baseUrl: String) {
        apiClientHolder.initializeUnauthenticatedClient(baseUrl = baseUrl)
    }

    fun initializeAuthenticatedApiClientHolder(baseUrl: String, accessToken: String) {
        apiClientHolder.initializeAuthenticatedClient(baseUrl = baseUrl, accessToken = accessToken)
    }

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