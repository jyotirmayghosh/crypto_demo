package com.jyotirmayg.cryptodemo.base

import com.jyotirmayg.cryptodemo.network.Resource
import com.jyotirmayg.cryptodemo.utilities.Print
import retrofit2.Response

/**
 * @author ANIL
 *
 * It will create the common things for all Repo. We may add few things later.
 * So your Repo must be derived from this.
 * */
open class BaseRepo {

    /**
     * Creates a loading state for the [Resource]
     * */
    suspend fun <T> loading() = Resource.loading<T>()

    /**
     * This makes a api call given in the param and applies result to [Resource]
     * @param call the api call function.
     * */
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> = try {
        val apiResult = call.invoke()
        if (apiResult.isSuccessful) {
            val successBody = apiResult.body()!!
            Resource.success(successBody)
        } else {
            val errorBody = apiResult.errorBody()!!.string()
            Print.e(errorBody)

            val error = when (apiResult.code()) {
                400 -> "Bad Request"
                401 -> "Authentication failed"
                403 -> "Forbidden"
                404 -> "Not Found"
                405 -> "Method Not Allowed"
                406 -> "Not Acceptable"
                407 -> "Proxy Authentication Required"
                408 -> "Request Timeout"
                500 -> "Internal Server Error"
                501 -> "Not Implemented"
                502 -> "Bad Gateway"
                503 -> "Service Unavailable"
                504 -> "Gateway Timeout"
                505 -> "HTTP Version Not Supported"
                else -> "An error occur"
            }
            Resource.error(message = error)
        }
    } catch (e: Exception) {
        Resource.error("${e.message}")
    }
}