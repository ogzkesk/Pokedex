package com.ogzkesk.home

sealed interface HomeEvent {

    object Loading: HomeEvent
    object Success: HomeEvent
    data class Error(val message: String): HomeEvent
}