package nl.pcstet.startupflow.ui.core.model

sealed interface InputValidationState {
    data object Idle : InputValidationState
    data object Loading : InputValidationState
    data object Valid : InputValidationState
    data class Invalid(val message: String) : InputValidationState
}

