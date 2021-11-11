package com.keelim.free.ui.main.dash.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.keelim.data.model.dash.Search2
import com.keelim.free.databinding.FragmentSearchDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import showToast

@AndroidEntryPoint
class SearchDetailFragment: Fragment() {
    private var _binding: FragmentSearchDetailBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        searchAdapter = SearchAdapter { text->
            editQuery.setText(text)
        }

        editQuery.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val query = editQuery.text.toString()
                if (query.isNotEmpty()) {
                    searchViewModel.search(query)
                } else {
                    requireContext().showToast("검색어를 입력해주세요")
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }
    }

    private fun observeData() = searchViewModel.state.observe(viewLifecycleOwner) {
        when (it) {
            is HistoryState.UnInitialized -> handleUnInitialized()
            is HistoryState.Loading -> handleLoading()
            is HistoryState.Success -> handleSuccess(it.data)
            is HistoryState.Error -> handleError()
            is HistoryState.SearchSuccess -> handleSearchSuccess(it.data)
        }
    }

    private fun handleUnInitialized() {
    }

    private fun handleLoading() {
//        requireActivity().showToast("검색 이력을 불러오고 있습니다.")
    }

    private fun handleSuccess(data: List<String>) {
        searchAdapter.apply {
            submitList(data)
        }
    }

    private fun handleError() {
        requireActivity().showToast("에러가 발생했습니다. 다시 한번 로드해주세요")
    }

    private fun handleSearchSuccess(data: List<Search2>) = with(binding) {

    }
    
    companion object {
        val samples = listOf("안드로이드", "DB", "Network", "OS", "AI", "Big Data")
    }
}