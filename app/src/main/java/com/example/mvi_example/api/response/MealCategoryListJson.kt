package com.example.mvi_example.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealCategoryListJson(
    @Json(name = "categories") val categories: List<MealCategoryJson>
)

@JsonClass(generateAdapter = true)
data class MealCategoryJson(
    @Json(name = "idCategory") val idCategory: Int,
    @Json(name = "strCategory") val strCategory: String,
    @Json(name = "strCategoryThumb") val strCategoryThumb: String,
    @Json(name = "strCategoryDescription") val strCategoryDescription: String
)
