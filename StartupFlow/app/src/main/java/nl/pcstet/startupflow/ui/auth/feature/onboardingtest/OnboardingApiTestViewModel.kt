package nl.pcstet.startupflow.ui.auth.feature.onboardingtest

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.pcstet.startupflow.data.auth.datasource.network.AuthApiService
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataSourceImpl
import nl.pcstet.startupflow.data.core.datasource.network.utils.onFailure
import nl.pcstet.startupflow.data.core.datasource.network.utils.onSuccess

data class ApiTestUiState(
    val apiUrl: String? = null,
    val testState: ApiTestStatus = ApiTestStatus.Idle,
)

sealed interface ApiTestStatus {
    data object Loading : ApiTestStatus
    data object Idle : ApiTestStatus
    data object Success : ApiTestStatus
    data class Error(val message: String) : ApiTestStatus
}

class OnboardingApiTestViewModel(
    private val authApiService: AuthApiService,
    private val settingsDataStore: SettingsDataSourceImpl,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val apiUrl = savedStateHandle.toOnboardingApiTestArgs().apiUrl

    private val _state = MutableStateFlow<ApiTestStatus>(ApiTestStatus.Idle)
    val state = _state.asStateFlow()

    init {
        testApiUrl()
    }

    fun testApiUrl() {
        if (_state.value != ApiTestStatus.Idle) return

        _state.value = ApiTestStatus.Loading
        viewModelScope.launch {
            val apiUrl = apiUrl
            val result = authApiService.test(apiUrl = apiUrl)

            result
                .onSuccess {
                    settingsDataStore.setApiUrl(apiUrl)
                    _state.value = ApiTestStatus.Success
                }
                .onFailure { apiException ->
                    _state.value = ApiTestStatus.Error(message = apiException.message)
                }

            Log.d("OnboardingViewModel", result.toString())
        }
    }
}
