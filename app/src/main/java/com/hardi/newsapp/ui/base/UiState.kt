package com.hardi.newsapp.ui.base

sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Error<T>(val throwable: Throwable?= null,val message: T? = null) : UiState<T>

    object Loading : UiState<Nothing>

}