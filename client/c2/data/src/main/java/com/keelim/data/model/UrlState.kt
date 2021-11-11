package com.keelim.data.model

sealed class UrlState {
    object UnInitialized : UrlState()
    object Loading : UrlState()
    object Success : UrlState()
    data class Error(val message: String) : UrlState()
}
