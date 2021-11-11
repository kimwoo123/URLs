package com.keelim.free.ui.main.dash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keelim.data.model.dash.DashState
import com.keelim.free.databinding.FragmentDashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class DashFragment : Fragment() {
    private var _binding:FragmentDashBinding? = null
    private val binding get() = _binding!!
    private val viewModel:DashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    private fun initViews() = with(binding){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.state.collect {
                when(it){
                    is DashState.Error -> {}
                    is DashState.Loading -> requireActivity().showToast("로딩 중입니다.")
                    is DashState.Success -> {
                        binding.descSection1.text = it.data.memos.toString()
                        binding.descSection2.text = it.data.folders.toString()
                    }
                    is DashState.UnInitialized -> {}
                }
            }
        }
    }
}