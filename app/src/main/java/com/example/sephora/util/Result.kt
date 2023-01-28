package com.example.sephora.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val message: String, val data: T? = null) : Result<T>()
}