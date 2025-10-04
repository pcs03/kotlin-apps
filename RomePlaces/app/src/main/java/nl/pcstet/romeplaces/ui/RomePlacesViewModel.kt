package nl.pcstet.romeplaces.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import nl.pcstet.romeplaces.LocalRomePlacesDataProvider
import nl.pcstet.romeplaces.model.RomePlace

class RomePlacesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        RomePlacesUiState(
            romePlacesList = LocalRomePlacesDataProvider.getRomePlacesData(),
            currentRomePlace = LocalRomePlacesDataProvider.getRomePlacesData().getOrElse(0) {
                LocalRomePlacesDataProvider.defaultRomePlace
            }
        )
    )

    val uiState: StateFlow<RomePlacesUiState> = _uiState

    fun updateCurrentRomePlace(selectedRomePlace: RomePlace) {
        _uiState.update { currentState ->
            currentState.copy(
                currentRomePlace = selectedRomePlace,
            )
        }
    }

    fun navigateToListPage() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingListPage = true
            )
        }
    }

    fun navigateToDetailPage() {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingListPage = false
            )
        }
    }
}

data class RomePlacesUiState(
    val romePlacesList: List<RomePlace> = emptyList(),
    val currentRomePlace: RomePlace = LocalRomePlacesDataProvider.defaultRomePlace,
    val isShowingListPage: Boolean = true,
)