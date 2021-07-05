package com.example.mvi_example.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object MoshiSingleton {
    val moshi = Moshi.Builder().build()

    inline fun <reified T> adapter(): JsonAdapter<T> = moshi.adapter(T::class.java)
}