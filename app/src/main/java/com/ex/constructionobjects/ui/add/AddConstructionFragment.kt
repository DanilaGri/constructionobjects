package com.ex.constructionobjects.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ex.constructionobjects.data.model.Construction
import com.ex.constructionobjects.data.model.ConstructionLocal
import com.ex.constructionobjects.databinding.FragmentAddConstructionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddConstructionFragment : Fragment() {

    private var _binding: FragmentAddConstructionBinding? = null
    private val viewModel: AddConstructionViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddConstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addConstructionButton.setOnClickListener {
            viewModel.addNewConstruction(getConstruction())

            findNavController().popBackStack()
        }
    }

    // get construction
    private fun getConstruction(): ConstructionLocal {
        val name = binding.addConstructionName.text.toString()
        val description = binding.addConstructionDescription.text.toString()
        val area = binding.addAreaDescription.text.toString()
        val district = binding.addDistrictDescription.text.toString()
        val floors = binding.addFloorsDescription.text.toString().toIntOrNull() ?: 0
        val price = binding.addPriceDescription.text.toString().toDoubleOrNull() ?: 0.0
        val type = binding.addTypeDescription.text.toString()
        val preview = binding.addConstructionImage.text.toString()

        return ConstructionLocal(0, name, description, area, district, floors, price, type, preview)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
