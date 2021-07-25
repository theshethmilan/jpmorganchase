package com.milan.jpmorganchase.api

data class ApiState<out T>(val ApiStatus: ApiStatus, val data: T?, val error: String?) {
    companion object {
        fun <T> success(data: T?): ApiState<T> =
            ApiState(ApiStatus = ApiStatus.SUCCESS, data = data, error = null)

        fun <T> error(errorMsg: String?): ApiState<T> =
            ApiState(ApiStatus = ApiStatus.ERROR, data = null, error = errorMsg)

        fun <T> loading(): ApiState<T> =
            ApiState(ApiStatus = ApiStatus.LOADING, data = null, error = null)
    }
}
