package com.jyotirmayg.cryptodemo.network

import retrofit2.Response


sealed class ApiResponse<T> : CommonResponse<T>() {

    companion object {
        private const val UNKNOWN_ERROR: Int = -1

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                ApiErrorResponse(
                    response.code(),
                    response.errorBody()?.string() ?: response.message()
                )
            }
        }

        fun <T> create(throwable: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(UNKNOWN_ERROR, throwable.message ?: "Unknown Error!")
        }

        fun <T> create(code: Int, throwable: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(code, throwable.message ?: "Response Unsuccessful")
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(
    val errorCode: Int,
    val errorMessage: String
) : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()


open class CommonResponse<T>