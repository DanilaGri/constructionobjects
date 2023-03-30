package com.ex.constructionobjects.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.constructionobjects.data.ConstructionRepository
import com.ex.constructionobjects.data.model.Construction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConstructionUiState(
    val isShowFilter: Boolean = false,
    val list: List<Construction> = emptyList()
)

@HiltViewModel
class ConstructionViewModel @Inject constructor(private val repository: ConstructionRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConstructionUiState())

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<ConstructionUiState> = _uiState

    init {
        getAllConstruction()
    }

    fun resetFilter() {
        getAllConstruction()
    }

    fun filterMenuPressed() {
        _uiState.value = uiState.value.copy(isShowFilter = true)
    }

    fun filterConstructionByDistrict(district: String) {
        viewModelScope.launch {
            repository.getConstructionByDistrict(district)
                .collect { construction ->
                    _uiState.value = uiState.value.copy(isShowFilter = true, list = construction)
                }
        }
    }
    fun filterConstructionByPriceRange(minPrice: Double, maxPrice: Double) {
        viewModelScope.launch {
            repository.getConstructionByPriceRange(minPrice, maxPrice)
                .collect { construction ->
                    _uiState.value = uiState.value.copy(isShowFilter = true, list = construction)
                }
        }
    }

    fun delete(construction: Construction) = viewModelScope.launch {
        repository.deleteConstruction(construction)
    }

    private fun getAllConstruction() {
        viewModelScope.launch {
            repository.getAllConstruction()
                .collect { constructions ->
                    _uiState.value = uiState.value.copy(isShowFilter = false, list = constructions)
                }
        }
    }
}
