package com.example.mvi_example.service

import com.example.mvi_example.core.network.NetworkRequestManager
import com.example.mvi_example.api.MealApi
import com.example.mvi_example.core.state.Result
import com.example.mvi_example.mapper.MealCategoryMapper
import com.example.mvi_example.model.MealCategory

class MealService(
    private val api: MealApi,
    private val mapper: MealCategoryMapper,
    private val networkRequestManager: NetworkRequestManager
) {

    suspend fun getMeals(): Result<List<MealCategory>> {
        return networkRequestManager.apiRequestWithMapping(
            apiCall = { api.getMealCategories() },
            mapTransform = { mapper.fromList(it.categories) }
        )
    }
}