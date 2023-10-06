package com.ex.constructionobjects.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.constructionobjects.data.ConstructionRepository
import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.data.model.ConstructionLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EditConstructionState {
    data class Success(val construction: ConstructionLocal? = null) : EditConstructionState()
    // later
//    data class Error(val exception: Throwable): EditConstructionState()
}

@HiltViewModel
class EditConstructionViewModel @Inject constructor(
    private val st: SavedStateHandle,
    private val repository: ConstructionRepository
) : ViewModel() {

    private val constructionId: Long = st.get<Long>(CONSTRUCTION_ID_STATE_KEY)!!

    private val _uiState = MutableStateFlow(EditConstructionState.Success(null))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<EditConstructionState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val construction = repository.getConstructionById(constructionId)
            _uiState.emit(_uiState.value.copy(construction = construction))
        }
    }

    fun updateConstruction(construction: ConstructionLocal) = viewModelScope.launch {
        repository.updateConstruction(construction)
    }

    companion object {
        private const val CONSTRUCTION_ID_STATE_KEY = "constructionId"
    }
}
