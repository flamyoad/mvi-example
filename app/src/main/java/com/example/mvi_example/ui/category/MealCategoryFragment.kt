package com.example.mvi_example.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi_example.core.state.State
import com.example.mvi_example.databinding.FragmentMealCategoryBinding
import com.example.mvi_example.model.MealCategory
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealCategoryFragment : Fragment(), Observer<State<List<MealCategory>>> {

    private var _binding: FragmentMealCategoryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MealCategoryViewModel by viewModel()

    private lateinit var categoryAdapter: MealCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryAdapter = MealCategoryAdapter {  }
        val linearLayoutManager = LinearLayoutManager(requireContext())

        with(binding.recyclerView) {
            adapter = categoryAdapter
            layoutManager = linearLayoutManager
        }

        viewModel.state.observe(viewLifecycleOwner, this)

        binding.loadingPlaceholderView.onRetry = {
            viewModel.retryInitialLoad()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadManually()
        }
    }

    override fun onChanged(state: State<List<MealCategory>>) {
        binding.loadingPlaceholderView.bind(state)
        binding.swipeRefreshLayout.isEnabled = state is State.Loaded || state is State.ManualReloadingFailed
        binding.swipeRefreshLayout.isRefreshing = state is State.ManualReloading

        if (state is State.Loaded) {
            categoryAdapter.categoryList = state.data
        }
    }
}