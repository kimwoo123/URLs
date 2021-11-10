package com.keelim.free.ui.main.dash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.keelim.free.R
import com.keelim.free.databinding.FragmentDashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashFragment : Fragment() {
    private var _binding:FragmentDashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}