package nl.pcstet.romeplaces.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import nl.pcstet.romeplaces.data.LocalRomePlacesDataProvider
import nl.pcstet.romeplaces.model.RomePlace
import nl.pcstet.romeplaces.model.RomePlaceCategory

class RomePlacesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RomePlacesUiState())
    val uiState: StateFlow<RomePlacesUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        val categories: Map<RomePlaceCategory, List<RomePlace>> =
            LocalRomePlacesDataProvider.getRomePlacesData().groupBy { romePlace ->
                romePlace.category
            }

        _uiState.value = RomePlacesUiState(
            categories = categories,
            currentCategory = LocalRomePlacesDataProvider.defaultCategory,
            currentRomePlace = LocalRomePlacesDataProvider.defaultRomePlace,
            isShowingListPage = true
        )
    }

    fun updateCurrentRomePlace(selectedRomePlace: RomePlace) {
        _uiState.update { currentState ->
            currentState.copy(
                currentRomePlace = selectedRomePlace,
                currentCategory = selectedRomePlace.category,
            )
        }
    }

    fun updateCurrentCategory(selectedCategory: RomePlaceCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCategory = selectedCategory,
                currentRomePlace = currentState.categories[selectedCategory]?.get(0)
                    ?: LocalRomePlacesDataProvider.defaultRomePlace,
                isShowingListPage = true
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
    val categories: Map<RomePlaceCategory, List<RomePlace>> = emptyMap(),
    val currentCategory: RomePlaceCategory = LocalRomePlacesDataProvider.defaultCategory,
    val currentRomePlace: RomePlace = LocalRomePlacesDataProvider.defaultRomePlace,
    val isShowingListPage: Boolean = true,
) {
    val currentCategoryPlaces: List<RomePlace> by lazy { categories[currentCategory]!! }
}