package com.keelim.data.model

sealed class DataState {
    object UnInitialized : DataState()
    object Loading : DataState()
    data class Error(val message: String) : DataState()
    data class Success(val data: List<Any>) : DataState()
}