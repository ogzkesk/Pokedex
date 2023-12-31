package com.ogzkesk.common.util

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val exception: Throwable? = null) : Resource<Nothing>
    object Loading : Resource<Nothing>
}