package com.ogzkesk.details

sealed interface DetailsEvent {

    object Loading : DetailsEvent
    object Success : DetailsEvent
    class Error(val message: String) : DetailsEvent
}