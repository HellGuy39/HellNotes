package com.hellguy39.hellnotes.core.model

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
}

//sealed class Resource {
//
//    data class Success<T>(val data: T): Resource()
//
//    data class Error(val message: String): Resource()
//
//    data class Loading(val isLoading: Boolean): Resource()
//
//}