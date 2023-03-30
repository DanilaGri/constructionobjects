package com.ex.constructionobjects.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ex.constructionobjects.R
import com.ex.constructionobjects.databinding.DialogDistrictFilterBinding
import com.ex.constructionobjects.databinding.DialogPriceFilterBinding
import com.ex.constructionobjects.databinding.FragmentConstructionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ConstructionFragment : Fragment(), MenuProvider {

    private var _binding: FragmentConstructionBinding? = null
    private var adapter: ConstructionAdapter? = null
    private val viewModel: ConstructionViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstructionBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация RecyclerView и адаптера
        adapter = ConstructionAdapter(object : ConstructionAdapter.OnConstructionClickListener {
            override fun onEdit(position: Int) {
                val currentList = adapter?.currentList?.toMutableList()
                currentList?.get(position)?.let {
                    val bundle = bundleOf("constructionId" to it.id)
                    findNavController().navigate(R.id.action_ConstructionFragment_to_editConstructionFragment, bundle)
                }
            }

            override fun onDelete(position: Int) {
                val currentList = adapter?.currentList?.toMutableList()
                currentList?.get(position)?.let { viewModel.delete(it) }
                currentList?.removeAt(position)
                adapter?.submitList(currentList)
            }
        })

        binding.recyclerView.adapter = adapter

        // наблюдение за списком объектов и обновление RecyclerView
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    adapter?.submitList(uiState.list)
                    binding.filterLayout.visibility = if (uiState.isShowFilter) View.VISIBLE else View.GONE
                }
            }
        }

        binding.fabAddConstruction.setOnClickListener {
            findNavController().navigate(R.id.action_ConstructionFragment_to_addConstructionFragment)
        }

        binding.filterDistrictButton.setOnClickListener {
            showDistrictFilterDialog()
        }

        binding.filterPriceButton.setOnClickListener {
            showPriceFilterDialog()
        }

        binding.filterCloseButton.setOnClickListener {
            viewModel.resetFilter()
        }
    }

    private fun showError(exception: Throwable) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_LONG).show()
    }

    private fun showDistrictFilterDialog() {
        val dialogBinding = DialogDistrictFilterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.filter_district_dialog_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.filter) { _, _ ->
                val district = dialogBinding.dFilterDistrictDescription.text.toString()
                viewModel.filterConstructionByDistrict(district)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
        dialog.show()
    }

    private fun showPriceFilterDialog() {
        val dialogBinding = DialogPriceFilterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.filter_price_dialog_title)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.filter) { _, _ ->
                val minPrice = dialogBinding.dMinPriceDescription.text.toString().toDoubleOrNull() ?: 0.0
                val maxPrice = dialogBinding.dMaxPriceDescription.text.toString().toDoubleOrNull() ?: Double.MAX_VALUE
                viewModel.filterConstructionByPriceRange(minPrice, maxPrice)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
        dialog.show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_filer -> {
                viewModel.filterMenuPressed()
                return true
            }
            else -> {}
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
