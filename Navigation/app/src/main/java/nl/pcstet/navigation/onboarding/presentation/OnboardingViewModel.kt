package nl.pcstet.navigation.onboarding.presentation

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.pcstet.navigation.main.data.network.ApiClientHolder
import nl.pcstet.navigation.core.data.utils.ApiResult
import nl.pcstet.navigation.main.data.local.UserPreferencesDataStore
import nl.pcstet.navigation.onboarding.network.OnboardingApiService

data class ApiInputUiState(
    val protocol: String = "https",
    val host: String = "",
    val path: String = "",
    val hostError: String? = null,
    val pathError: String? = null,
    val isFormValid: Boolean = false,
)

sealed interface ApiTestUiState {
    data object Loading : ApiTestUiState
    data object Idle : ApiTestUiState
    data class Success(val url: String) : ApiTestUiState
    data class Error(val message: String) : ApiTestUiState
}

class OnboardingViewModel(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val onboardingApiService: OnboardingApiService,
    private val apiClientHolder: ApiClientHolder,
) : ViewModel() {
    private val _apiInputUiState = MutableStateFlow<ApiInputUiState>(ApiInputUiState())
    val apiInputUiState = _apiInputUiState.asStateFlow()

    private val _apiTestUiState = MutableStateFlow<ApiTestUiState>(ApiTestUiState.Idle)
    val apiTestUiState = _apiTestUiState.asStateFlow()


    fun changeApiScheme(scheme: String) {
        val isFormValid = validateForm()
        _apiInputUiState.update { it.copy(protocol = scheme, isFormValid = isFormValid) }
    }

    fun changeApiHost(host: String) {
        val hostError = validateHost(host)
        val isFormValid = validateForm()
        _apiInputUiState.update { it.copy(host = host, hostError = hostError, isFormValid = isFormValid) }
    }

    fun changeApiPath(path: String) {
        val pathError = validatePath(path)
        val isFormValid = validateForm()
        _apiInputUiState.update { it.copy(path = path, pathError = pathError, isFormValid = isFormValid) }
    }

    fun startApiTest() {
        viewModelScope.launch {
            _apiTestUiState.value = ApiTestUiState.Loading

            val url = constructUrl()
            apiClientHolder.initializeUnauthenticatedClient(url)

            Log.d("OnboardingViewModel", url)
            val result = onboardingApiService.test()

            when (result) {
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

    private suspend fun saveUrlToDataStore(baseUrl: String) {
        userPreferencesDataStore.setBackendUri(baseUrl)
    }

    private fun validateHost(host: String): String? {
        if (host.isBlank()) {
            return null
        }

        if (host.any { it.isWhitespace() }) {
            return "Host cannot contain spaces."
        }

        if (host.startsWith(".") || host.endsWith(".")) {
            return "Host cannot start or end with '.'"
        }

        if (host.startsWith("-") || host.endsWith("-")) {
            return "Host cannot start or end with '-'"
        }

        if (host.contains("..")) {
            return "Host cannot contain '..'"
        }

        if (!Patterns.DOMAIN_NAME.matcher(host).matches()) {
            return "Host is invalid."
        }

        return null
    }

    private fun validatePath(path: String): String? {
        // The TextField start with a '/'
        if (path.startsWith("/")) {
            return "Path cannot contain '//'"
        }

        if (path.length > 1 && path.contains("//")) {
            return "Path cannot contain '//'"
        }

        if (path.endsWith("/")) {
            return "Path cannot end with '/'"
        }

        if (path.any { it.isWhitespace() }) {
            return "Path cannot contain whitespace"
        }

        if (path.contains("?") || path.contains("#")) {
            return "Path cannot contain '?' or '#"
        }

        val invalidPathCharRegex = Regex("[^a-zA-Z0-9-._~:!$&'()*+,;=/@/]")
        if (invalidPathCharRegex.containsMatchIn(path)) {
            return "Path contains invalid characters."
        }

        return null
    }

    private fun validateForm(): Boolean {
        val host = _apiInputUiState.value.host
        val hostError = _apiInputUiState.value.hostError
        val pathError = _apiInputUiState.value.pathError

        val hostIsValid = host.isNotBlank() && hostError == null
        val pathIsValid = pathError == null

        return hostIsValid && pathIsValid
    }

    private fun constructUrl(): String {
        val protocol = _apiInputUiState.value.protocol
        val finalHost = _apiInputUiState.value.host.trim().removeSuffix("/")
        val finalPath = _apiInputUiState.value.path.trim()

        val finalUrl = "$protocol://$finalHost/$finalPath"

        if (finalUrl.endsWith("/")) {
            return finalUrl
        }
        return "$finalUrl/"
    }

}
