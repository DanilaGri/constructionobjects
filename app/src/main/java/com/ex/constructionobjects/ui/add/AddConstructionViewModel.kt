package com.ex.constructionobjects.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.constructionobjects.data.ConstructionRepository
import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.data.model.ConstructionLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddConstructionViewModel @Inject constructor(private val repository: ConstructionRepository) : ViewModel() {

    fun addNewConstruction(constructionObject: ConstructionLocal) = viewModelScope.launch {
        repository.addNewConstruction(constructionObject)
    }
}
