package com.keelim.free.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.free.util.OpenGraphParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val parser: OpenGraphParser
):ViewModel() {
    private val _openText = MutableSharedFlow<String>()
    val openText = _openText.asSharedFlow()

    suspend fun openGraphClick(url:String) = viewModelScope.launch {
        val ogTags = parser.parse(url)
        _openText.emit(ogTags.toString())
    }
}