package nl.pcstet.startupflow.ui.auth.feature.landing

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.pcstet.startupflow.data.auth.repository.AuthRepository
import nl.pcstet.startupflow.data.auth.repository.model.ApiTestResult
import nl.pcstet.startupflow.data.auth.repository.model.LoginCredentials
import nl.pcstet.startupflow.data.utils.onFailure
import nl.pcstet.startupflow.ui.core.base.BaseViewModel
import nl.pcstet.startupflow.ui.core.model.InputValidationState

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class LandingViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<LandingState, LandingEvent, LandingAction>(
    initialState = LandingState.Loading
) {
    init {
        initializeState()
        watchApiUrlInputForValidation()
    }

    private fun initializeState() {
        viewModelScope.launch {
            val storedApiUrl = authRepository.apiUrl.firstOrNull() ?: ""
            val storedEmail = authRepository.rememberedEmail.firstOrNull() ?: ""

            val initialState = LandingState.Content(
                apiUrlInput = storedApiUrl,
                apiUrlValidation = if (storedApiUrl.isNotEmpty()) InputValidationState.Valid else InputValidationState.Idle,
                emailInput = storedEmail,
                emailValidation = if (storedEmail.isNotEmpty()) InputValidationState.Valid else InputValidationState.Idle,
                rememberEmailEnabled = storedEmail.isNotEmpty()
            )

            mutableStateFlow.update { initialState }
        }
    }

    private fun watchApiUrlInputForValidation() {
        mutableStateFlow
            .map { (it as? LandingState.Content)?.apiUrlInput }
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { input ->
                when (val patternValidation = validateApiUrlByPattern(input)) {
                    is InputValidationState.Valid -> flow {
                        emit(InputValidationState.Loading)
                        delay(1000L)
                        emitAll(validateApiUrlByNetworkCall(input))
                    }

                    else -> flowOf(patternValidation)
                }
            }
            .onEach { result ->
                mutableStateFlow.updateIfContent { currentState ->
                    currentState.copy(apiUrlValidation = result)
                }
            }
            .launchIn(viewModelScope)
    }

    // Action handling

    override fun handleAction(action: LandingAction) {
        when (action) {
            is LandingAction.ApiUrlInputChanged -> handleApiUrlInputChanged(action)
            is LandingAction.EmailInputChanged -> handleEmailInputChanged(action)
            is LandingAction.PasswordInputChanged -> handlePasswordInputChanged(action)
            is LandingAction.ContinueButtonClick -> handleContinueButtonClicked()
            is LandingAction.RememberEmailSwitchClicked -> handleRememberEmailSwitchClicked(action)

            is LandingAction.Internal.ApiUrlValidationChanged -> handleApiUrlValidationChanged(
                action
            )
        }
    }

    private fun handleApiUrlInputChanged(action: LandingAction.ApiUrlInputChanged) {
        mutableStateFlow.updateIfContent { currentState ->
            currentState.copy(apiUrlInput = action.input)
        }
    }

    private fun handleEmailInputChanged(action: LandingAction.EmailInputChanged) {
        mutableStateFlow.updateIfContent { currentState ->
            val emailValidation = validateEmailByPattern(action.input)
            currentState.copy(
                emailInput = action.input,
                emailValidation = emailValidation
            )
        }
    }

    private fun handlePasswordInputChanged(action: LandingAction.PasswordInputChanged) {
        mutableStateFlow.updateIfContent { currentState ->
            currentState.copy(passwordInput = action.input)
        }
    }

    private fun handleApiUrlValidationChanged(action: LandingAction.Internal.ApiUrlValidationChanged) {
        mutableStateFlow.updateIfContent { currentState ->
            currentState.copy(apiUrlValidation = action.input)
        }
    }

    private fun handleRememberEmailSwitchClicked(action: LandingAction.RememberEmailSwitchClicked) {
        mutableStateFlow.updateIfContent { currentState ->
            currentState.copy(rememberEmailEnabled = action.input)
        }
    }

    private fun handleContinueButtonClicked() {
        val currentState = state
        if (currentState is LandingState.Content) {
            if (!currentState.continueButtonEnabled) {
                return
            }

            viewModelScope.launch {
                val loginResult = authRepository.login(
                    apiUrl = currentState.apiUrlInput,
                    credentials = LoginCredentials(
                        email = currentState.emailInput,
                        password = currentState.passwordInput,
                    ),
                    rememberMe = currentState.rememberEmailEnabled,
                )
                loginResult.onFailure {
                    sendEvent(LandingEvent.LoginError("Login failed! Please check your inputs."))
                }
            }

        }
    }

    // Private helper functions

    private suspend fun validateApiUrlByNetworkCall(apiUrl: String): Flow<InputValidationState> {
        return authRepository.testApiUrlValid(apiUrl).map { apiTestResult ->
            when (apiTestResult) {
                is ApiTestResult.Loading -> InputValidationState.Loading
                is ApiTestResult.Success -> InputValidationState.Valid
                is ApiTestResult.Failure -> InputValidationState.Invalid(apiTestResult.message)
            }
        }
    }

    private fun validateApiUrlByPattern(apiUrl: String): InputValidationState {
        if (apiUrl.isBlank()) return InputValidationState.Idle
        val validUrl = Patterns.WEB_URL.matcher(apiUrl).matches()
        return when (validUrl) {
            true -> InputValidationState.Valid
            false -> InputValidationState.Invalid("The specified API URL is invalid.")
        }
    }

    private fun validateEmailByPattern(email: String): InputValidationState {
        if (email.isBlank()) return InputValidationState.Idle
        return InputValidationState.Valid
        val validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return when (validEmail) {
            true -> InputValidationState.Valid
            false -> InputValidationState.Invalid("The specified email is invalid.")
        }
    }
}

sealed interface LandingState {
    data object Loading : LandingState
    data class Content(
        val apiUrlInput: String = "",
        val apiUrlValidation: InputValidationState = InputValidationState.Idle,
        val emailInput: String = "",
        val emailValidation: InputValidationState = InputValidationState.Idle,
        val passwordInput: String = "",
        val passwordValidation: InputValidationState = InputValidationState.Idle,
        val rememberEmailEnabled: Boolean = false,
    ) : LandingState {
        val continueButtonEnabled: Boolean
            get() = apiUrlValidation is InputValidationState.Valid && emailValidation is InputValidationState.Valid
    }
}

sealed interface LandingEvent {
//    data class NavigateToLogin(val email: String) : LandingEvent
    data class LoginError(val message: String) : LandingEvent
}

sealed interface LandingAction {
    data object ContinueButtonClick : LandingAction
    data class ApiUrlInputChanged(val input: String) : LandingAction
    data class EmailInputChanged(val input: String) : LandingAction
    data class PasswordInputChanged(val input: String) : LandingAction
    data class RememberEmailSwitchClicked(val input: Boolean) : LandingAction

    sealed interface Internal : LandingAction {
        data class ApiUrlValidationChanged(val input: InputValidationState) : LandingAction
    }
}

private inline fun MutableStateFlow<LandingState>.updateIfContent(
    update: (LandingState.Content) -> LandingState,
) {
    this.update { currentState ->
        if (currentState is LandingState.Content) {
            update(currentState)
        } else {
            currentState
        }
    }
}