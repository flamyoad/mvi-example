package com.example.mvi_example.ui.category

import androidx.lifecycle.ViewModel
import com.example.mvi_example.core.state.DataLoadingViewModel
import com.example.mvi_example.model.MealCategory
import com.example.mvi_example.service.MealService
import com.example.mvi_example.core.state.Result

class MealCategoryViewModel(
    private val mealService: MealService
) : DataLoadingViewModel<List<MealCategory>>() {

    init {
        proceedToLoad()
    }

    override suspend fun load(): Result<List<MealCategory>> {
        return mealService.getMeals()
    }
}