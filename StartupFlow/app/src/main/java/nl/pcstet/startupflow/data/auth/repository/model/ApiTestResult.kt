package nl.pcstet.startupflow.data.auth.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface ApiTestResult {
    data object Success : ApiTestResult
    data class Failure(val message: String): ApiTestResult
    data object Loading : ApiTestResult
}
