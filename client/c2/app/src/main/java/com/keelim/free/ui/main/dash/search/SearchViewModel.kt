package com.keelim.free.ui.main.dash.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private var _state  = MutableLiveData<HistoryState>(HistoryState.UnInitialized)
    val state:LiveData<HistoryState> get() = _state


    fun search(query: String) = viewModelScope.launch {

    }
}