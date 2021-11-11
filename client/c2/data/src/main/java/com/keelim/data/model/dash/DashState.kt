package com.keelim.data.model.dash

sealed class DashState {
    object UnInitialized : DashState()
    object Loading : DashState()
    data class Error(val message: String) : DashState()
    data class Success(
        val data: Dash
    ) : DashState()
}