package com.example.mvi_example.core.network

import com.example.mvi_example.api.MoshiSingleton
import com.example.mvi_example.core.state.Result
import com.example.mvi_example.utils.guard
import okhttp3.ResponseBody
import retrofit2.Response
import com.example.mvi_example.core.network.AppError.Code
import com.squareup.moshi.JsonDataException

class NetworkRequestManager {

    suspend inline fun <reified T> apiRequest(apiCall: () -> Response<T>): Result<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body().guard {
                    // Some requests might return empty response body even though the request
                    // was succeeded, the check below verifies whether the callers care about the
                    // nullability of the response body.
                    return if (
                        ResponseBody::class.java.isAssignableFrom(T::class.java) ||
                        Any::class.java.isAssignableFrom(T::class.java)
                    ) {
                        // TODO: Improve nullability check.
                        Result.Success(Any() as T)
                    } else {
                        val error = AppError(Code.InvalidData)
                        Result.Failure(error)
                    }
                }

                return Result.Success(body)
            } else {
                handleFailureInResponse(response)
            }
        } catch (exception: Exception) {
            handleFailureInRequest(exception)
        }
    }

    inline fun <reified T> handleFailureInRequest(throwable: Throwable): Result<T> {
        return if (throwable is JsonDataException) {
            val error = AppError(Code.DataSerialization, throwable)
            Result.Failure(error)
        } else {
            val error = AppError(Code.Network, throwable)
            Result.Failure(error)
        }
        return Result.Failure(throwable)
    }

    inline fun <reified T> handleFailureInResponse(response: Response<T>): Result<T> {
        return Result.Failure(getAppError(response))
    }

    fun <T> getAppError(response: Response<T>): AppError {
        // Placeholder error that will be used unless replaced by a more specific error.
        var error = AppError(Code.BadRequest)
        val errorAdapter = MoshiSingleton.adapter<ApiError>()
        val responseError = errorAdapter.fromJson(response.errorBody()?.toString())
        when (response.code()) {
            400 -> {
                return error
            }

            500 -> {
                error = AppError(Code.ServerError)
                return error
            }

            else -> {
                try {
                    when (responseError?.code) {
                        // Contains customized error returned by backend API
                        // Invalid email, invalid password etc
                    }
                    return error
                } catch (exception: java.lang.Exception) {
                    return error
                }
            }
        }
    }
}
