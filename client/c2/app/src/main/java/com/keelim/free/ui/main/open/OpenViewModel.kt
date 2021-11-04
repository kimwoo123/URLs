package com.keelim.free.ui.main.open

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.open.OpenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OpenViewModel @Inject constructor(
//    private val ogTagParser: OgTagParser
):ViewModel() {
    private val _state: MutableStateFlow<OpenState> = MutableStateFlow(OpenState.UnInitialized)
    val state:StateFlow<OpenState> get() = _state

    fun getContent(linkArray:String) = viewModelScope.launch {
        _state.emit(OpenState.Loading)
        val result = runCatching {
//            ogTagParser.getContents(
//                linkArray,
//                object : LinkViewCallback {
//                    override suspend fun onAfterLoading(linkSourceContent: LinkSourceContent){
//                        _state.emit(OpenState.Success(linkSourceContent))
//                    }
//                }
//            )
        }
        when {
            result.isFailure -> {
                _state.emit(OpenState.Error("url을 다시 한번 입력해주시기 바랍니다."))
            }
        }
    }
}