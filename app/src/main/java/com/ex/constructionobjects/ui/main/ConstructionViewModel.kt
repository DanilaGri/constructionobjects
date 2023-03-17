package com.ex.constructionobjects.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.constructionobjects.data.ConstructionRepository
import com.ex.constructionobjects.data.model.Construction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ConstructionUiState {
    data class Success(val list: List<Construction>): ConstructionUiState()
    data class Error(val exception: Throwable): ConstructionUiState()
}

@HiltViewModel
class ConstructionViewModel @Inject constructor(private val repository: ConstructionRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConstructionUiState.Success(emptyList()))
    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<ConstructionUiState> = _uiState

    init {
        getAllConstruction()
    }

    fun resetFilter(){
        getAllConstruction()
    }

    fun filterConstructionByDistrict(district: String) {
        viewModelScope.launch {
            repository.getConstructionByDistrict(district)
                .collect { construction ->
                    _uiState.value = ConstructionUiState.Success(construction)
                }
        }
    }
    fun filterConstructionByPriceRange(minPrice: Double, maxPrice: Double) {
        viewModelScope.launch {
            repository.getConstructionByPriceRange(minPrice, maxPrice)
                .collect { construction ->
                    _uiState.value = ConstructionUiState.Success(construction)
                }
        }
    }

    fun delete(construction: Construction) = viewModelScope.launch {
        repository.deleteConstruction(construction)
    }

    private fun getAllConstruction(){
        viewModelScope.launch {
            repository.getAllConstruction()
                .collect { favoriteNews ->
                    _uiState.value = ConstructionUiState.Success(favoriteNews)
                }
        }
    }

}