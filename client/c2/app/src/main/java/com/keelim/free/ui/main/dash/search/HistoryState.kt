package com.keelim.free.ui.main.dash.search

import com.keelim.data.model.dash.Search2

sealed class HistoryState {
    object UnInitialized : HistoryState()
    object Loading : HistoryState()
    data class Success(
        val data: List<String>,
    ) : HistoryState()

    object Error : HistoryState()
    data class SearchSuccess(
        val data: List<Search2>,
    ) : HistoryState()
}