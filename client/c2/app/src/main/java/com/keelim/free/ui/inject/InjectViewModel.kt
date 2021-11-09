package com.keelim.free.ui.inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.UrlState
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class InjectViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<UrlState> = MutableStateFlow(UrlState.UnInitialized)
    val state: StateFlow<UrlState> get() = _state

    fun inject(token: String, url: String) = viewModelScope.launch {
        _state.emit(UrlState.Loading)
        val result = kotlin.runCatching { urlUseCase.share(token) }

        when {
            result.isSuccess -> {
                _state.emit(UrlState.Success)
            }

            result.isFailure -> {
                _state.emit(UrlState.Error("에러를 표시해줍니다."))
            }

            else -> {
                _state.emit(UrlState.Error("에러를 표시해줍니다."))
            }
        }
    }

    fun submitUrl(token:String, url: String) = viewModelScope.launch {
        _state.emit(UrlState.Loading)
        val result = kotlin.runCatching { urlUseCase.submitUrl(token, url) }
        when {
            result.isSuccess -> {
                _state.emit(UrlState.Success)
            }
            result.isFailure -> {
                _state.emit(UrlState.Error("에러를 표시해줍니다."))
            }
            else -> Unit
        }
    }
}