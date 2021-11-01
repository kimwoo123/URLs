package com.keelim.free.ui.main.open

import com.keelim.data.model.open.LinkSourceContent

sealed class OpenState{
    object UnInitialized : OpenState()
    object Loading : OpenState()
    data class Success(val data: LinkSourceContent) : OpenState()
    data class Error(val message: String) : OpenState()
}
