package com.example.mvi_example.di

import com.example.mvi_example.mapper.MealCategoryMapper
import org.koin.dsl.module

val mapperModules = module {
    single { MealCategoryMapper() }
}