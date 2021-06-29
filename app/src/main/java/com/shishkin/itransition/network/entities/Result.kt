package com.shishkin.itransition.network.entities

import com.shishkin.itransition.network.entities.Result.Status.ERROR
import com.shishkin.itransition.network.entities.Result.Status.LOADING
import com.shishkin.itransition.network.entities.Result.Status.SUCCESS

typealias KResult<T> = kotlin.Result<T>

data class Result<out T>(val status: Status, val data: T?, val error: Throwable?, val message: String?) {

    fun fold(
        onLoading: () -> Unit,
        onSuccess: (result: T?) -> Unit,
        onError: (throwable: Throwable?, message: String?) -> Unit
    ) {
        when(this.status) {
          SUCCESS -> onSuccess(data)
          ERROR -> onError(error, message)
          LOADING -> onLoading()
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