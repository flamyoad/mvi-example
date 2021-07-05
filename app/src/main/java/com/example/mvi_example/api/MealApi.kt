package com.example.mvi_example.api

import com.example.mvi_example.api.response.MealCategoryListJson
import retrofit2.Response
import retrofit2.http.GET

interface MealApi {

    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }

    @GET("categories.php")
    suspend fun getMealCategories(): Response<MealCategoryListJson>
}