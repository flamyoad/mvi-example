package com.example.mvi_example.core.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    val message: String,
    val code: String,
    val errors: List<FieldInfo>
)

@JsonClass(generateAdapter = true)
data class FieldInfo(
    val field: String?,
    val code: String?,
    val resource: String?
)
