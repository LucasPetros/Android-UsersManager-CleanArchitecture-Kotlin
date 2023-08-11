package com.lucas.petros.commons.data

sealed class DataResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : DataResource<T>(data)
    class Error<T>(message: String, data: T? = null) : DataResource<T>(data, message)
    class Loading<T>(data: T? = null) : DataResource<T>(data)
}
