package nl.pcstet.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import nl.pcstet.amphibians.data.LocalAmphibiansDataSource
import nl.pcstet.amphibians.model.Amphibian
import okio.IOException


sealed interface AmphibianUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibianUiState
    object Loading : AmphibianUiState
    object Error : AmphibianUiState
}

class AmphibiansViewModel : ViewModel() {
    var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        amphibianUiState = try {
            AmphibianUiState.Success(LocalAmphibiansDataSource.getAmphibians())
        } catch (e: IOException) {
            AmphibianUiState.Error
        }
    }
}