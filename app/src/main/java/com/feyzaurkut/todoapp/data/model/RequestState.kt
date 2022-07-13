package com.feyzaurkut.todoapp.data.model

sealed class RequestState<T>{
    class Loading<T>: RequestState<T>()
    data class Error<T>(val exception: Throwable): RequestState<T>()
    data class Success<T>(val data: T): RequestState<T>()
}
