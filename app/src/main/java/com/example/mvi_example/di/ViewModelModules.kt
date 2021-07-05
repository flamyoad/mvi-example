package com.example.mvi_example.di

import com.example.mvi_example.ui.category.MealCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MealCategoryViewModel(get()) }
}