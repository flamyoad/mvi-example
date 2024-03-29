package com.example.mvi_example.core.network

class AppError(
    val code: Code,
    underlyingError: Throwable? = null
) : RuntimeException() {
    enum class Code {
        DataSerialization,
        InvalidData,
        UrlError,
        Network,

        // HTTP Status Code 400 range.
        Authentication,
        BadRequest,
        NotFound,

        // HTTP Status code 500 range.
        ServerError,
    }
}
