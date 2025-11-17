package nl.pcstet.startupflow.ui.auth.feature.landing

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.pcstet.startupflow.data.auth.datasource.network.AuthApiService
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.data.auth.repository.di.authRepositoryModule
import nl.pcstet.startupflow.data.core.datasource.disk.SettingsDataSourceImpl
import nl.pcstet.startupflow.ui.core.base.BaseViewModel

data class ApiInputUiState(
    val protocol: String = "https",
    val host: String = "",
    val path: String = "",
    val hostError: String? = null,
    val pathError: String? = null,
    val isFormValid: Boolean = false,
)

private const val KEY_STATE = "state"

data class LandingState(
    val apiUrlInput: String,
    val emailInput: String,
    val continueButtonEnabled: Boolean,
    val rememberEmailEnabled: Boolean,

    )

sealed interface LandingEvent{
    data object NavigateBack : LandingEvent
}

sealed interface LandingAction {
    data object ContinueButtonClick : LandingAction
    data class EmailInputChanged(val input: String) : LandingAction
    data class ApiUrlInputChanged(val input: String) : LandingAction
}

class LandingViewModel(
    private val authApiService: AuthApiService,
    private val authRepository: AuthRepository,
    private val settingsDataStore: SettingsDataSourceImpl,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LandingState, LandingEvent, LandingAction>(
    initialState = savedStateHandle[KEY_STATE]
        ?: LandingState(
            emailInput = authRepository.authState.value.
        )
) {
    private val _uiState = MutableStateFlow<ApiInputUiState>(ApiInputUiState())
    val uiState = _uiState.asStateFlow()

    fun changeApiProtocol(scheme: String) {
        val isFormValid = validateForm()
        _uiState.update { it.copy(protocol = scheme, isFormValid = isFormValid) }
    }

    fun changeApiHost(host: String) {
        val hostError = validateHost(host)
        val isFormValid = validateForm()
        _uiState.update {
            it.copy(
                host = host,
                hostError = hostError,
                isFormValid = isFormValid
            )
        }
    }

    fun changeApiPath(path: String) {
        val pathError = validatePath(path)
        val isFormValid = validateForm()
        _uiState.update {
            it.copy(
                path = path,
                pathError = pathError,
                isFormValid = isFormValid
            )
        }
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
        val host = _uiState.value.host
        val hostError = _uiState.value.hostError
        val pathError = _uiState.value.pathError

        val hostIsValid = host.isNotBlank() && hostError == null
        val pathIsValid = pathError == null

        return hostIsValid && pathIsValid
    }

    private fun constructUrl(protocol: String, host: String, path: String): String {
        val finalHost = host.trim().removeSuffix("/")
        val finalPath = path.trim()

        return if (finalPath.isBlank()) {
            "$protocol://$finalHost"
        } else {
            "$protocol://$finalHost/$finalPath"
        }
    }

}
