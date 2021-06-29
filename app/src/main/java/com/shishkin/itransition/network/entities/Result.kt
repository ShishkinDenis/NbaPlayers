package com.shishkin.itransition.network.entities

typealias KResult<T> = kotlin.Result<T>

data class Result<out T>(val status: Status, val data: T?, val error: Throwable?, val message: String?) {

    fun fold(
            onLoading: () -> Unit,
            onSuccess: (result: T?) -> Unit,
            onError: (throwable: Throwable?, message: String?) -> Unit
    ) {
        when(this.status) {
            Status.SUCCESS -> onSuccess(data)
            Status.ERROR -> onError(error, message)
            Status.LOADING -> onLoading()
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String?, error: Throwable?): Result<T> {
            return Result(Status.ERROR, null, error, message)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}