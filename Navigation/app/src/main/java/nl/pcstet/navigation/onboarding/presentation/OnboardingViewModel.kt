package nl.pcstet.navigation.onboarding.presentation

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore
import nl.pcstet.navigation.onboarding.network.OnboardingApiService

data class ApiInputUiState(
    val scheme: String = "https",
    val host: String = "",
    val apiPath: String = "/",
    val isUrlValid: Boolean = false,
    val validationError: String? = null,
)

sealed interface ApiTestUiState {
    data object Loading : ApiTestUiState
    data object Idle : ApiTestUiState
    data class Success(val url: String) : ApiTestUiState
    data class Error(val message: String) : ApiTestUiState
}

class OnboardingViewModel(
    private val onboardingApiService: OnboardingApiService,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : ViewModel() {
    private val _apiInputUiState = MutableStateFlow<ApiInputUiState>(ApiInputUiState())
    val apiInputUiState = _apiInputUiState.asStateFlow()

    private val _apiTestUiState = MutableStateFlow<ApiTestUiState>(ApiTestUiState.Idle)
    val apiTestUiState = _apiTestUiState.asStateFlow()

    fun validateUrl(): Unit {
        val url =
            "${_apiInputUiState.value.scheme}://${_apiInputUiState.value.host}/${_apiInputUiState.value.apiPath}"
        val isUrlValid = Patterns.WEB_URL.matcher(url).matches()
        _apiInputUiState.update { it.copy(isUrlValid = isUrlValid) }
    }

    suspend fun saveUrlToDataStore(baseUrl: String) {
        userPreferencesDataStore.setBackendUri(baseUrl)
    }

    fun changeApiScheme(scheme: String) {
        _apiInputUiState.update { it.copy(scheme = scheme) }
        validateUrl()
    }

    fun changeApiHost(host: String) {
        _apiInputUiState.update { it.copy(host = host) }
        validateUrl()
    }

    fun changeApiPath(path: String) {
        _apiInputUiState.update { it.copy(apiPath = path) }
        validateUrl()
    }

    fun startApiTest() {
        viewModelScope.launch {
            _apiTestUiState.value = ApiTestUiState.Loading
            val url =
                "${_apiInputUiState.value.scheme}://${_apiInputUiState.value.host}${_apiInputUiState.value.apiPath}"

            Log.d("OnboardingViewModel", url)
            val result = onboardingApiService.test(url)

            when(result) {
                is ApiResult.Success -> {
                    _apiTestUiState.update { ApiTestUiState.Success(url) }
                    saveUrlToDataStore(url)
                }
                is ApiResult.Failure -> {
                    _apiTestUiState.update { ApiTestUiState.Error(result.cause.errorMsg) }
                }
            }
            Log.d("OnboardingViewModel", result.toString())
        }
    }

    init {
        Log.d("OnboardingViewModel", "Instantiated")
    }
}
