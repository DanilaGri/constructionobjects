package com.ex.constructionobjects.ui.edit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.databinding.FragmentEditConstructionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditConstructionFragment : Fragment() {

    private var _binding: FragmentEditConstructionBinding? = null
    private val viewModel: EditConstructionViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: EditConstructionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditConstructionBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {uiState ->
                    // New value received
                    when (uiState) {
                        is EditConstructionState.Success -> uiState.construction?.let { updateUI(it) }
                    }
                }
        }

        binding.saveObjectButton.setOnClickListener {
            viewModel.updateConstruction(getUpdatedConstruction())
            // return to previous screen
            findNavController().navigateUp()
        }


    }

    // update UI with construction data
    private fun updateUI(construction : Construction) {
        binding.edConstructionName.setText(construction.name)
        binding.edConstructionDescription.setText(construction.description)
        binding.edAreaDescription.setText(construction.area)
        binding.edDistrictDescription.setText(construction.district)
        binding.edFloorsDescription.setText(construction.floors.toString())
        binding.edPriceDescription.setText(construction.price.toString())
        binding.edTypeDescription.setText(construction.type)
        binding.edConstructionImage.setText(construction.preview)
    }

    // get updated construction
    private fun getUpdatedConstruction() : Construction{
        val constructionId = args.constructionId
        val name = binding.edConstructionName.text.toString()
        val description = binding.edConstructionDescription.text.toString()
        val area = binding.edAreaDescription.text.toString()
        val district = binding.edDistrictDescription.text.toString()
        val floors = binding.edFloorsDescription.text.toString().toIntOrNull() ?: 0
        val price = binding.edPriceDescription.text.toString().toDoubleOrNull() ?: 0.0
        val type = binding.edTypeDescription.text.toString()
        val preview = binding.edConstructionImage.text.toString()

        return Construction(constructionId,name,description,area,district,floors,price,type,preview)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}