package com.example.mvi_example.mapper

import com.example.mvi_example.api.response.MealCategoryJson
import com.example.mvi_example.model.MealCategory

class MealCategoryMapper: Mapper<MealCategoryJson, MealCategory> {
    override fun from(i: MealCategoryJson?): MealCategory {
        return MealCategory(
            id = i?.idCategory ?: -1,
            name = i?.strCategory ?: "",
            thumbnailUrl = i?.strCategoryThumb ?: "",
            description = i?.strCategoryDescription ?: ""
        )
    }

    override fun to(o: MealCategory?): MealCategoryJson {
        throw NotImplementedError()
    }
}