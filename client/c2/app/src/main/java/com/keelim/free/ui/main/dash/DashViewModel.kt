package com.keelim.free.ui.main.dash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.dash.DashState
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
): ViewModel() {
    private val _state: MutableStateFlow<DashState> = MutableStateFlow(DashState.UnInitialized)
    val state: StateFlow<DashState> = _state

    
    init{
        init()
    }

    fun init() = viewModelScope.launch{
        _state.emit(DashState.Loading)
        runCatching {
            urlUseCase.folderUrlMe()
        }.onSuccess {
            _state.emit(
                DashState.Success(it))
        }.onFailure {
            _state.emit(DashState.Error("에러가 발생하였습니다."))
        }
    }
}