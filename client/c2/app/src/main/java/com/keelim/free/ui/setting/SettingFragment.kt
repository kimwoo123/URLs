package com.keelim.free.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keelim.free.R
import com.keelim.free.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment: BottomSheetDialogFragment() {
    private var _binding:FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val settingViewModel:SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click()
    }

    private fun click() {
        binding.themeOption.setOnClickListener {
            dismiss()
        }

        binding.aboutButton.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.aboutFragment)
        }

        binding.login.setOnClickListener {
            dismiss()
//            findNavController().navigate(R.id.profileFragment)
        }

        binding.openSourceLicensesButton.setOnClickListener {
            dismiss()
            requireActivity().startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
        }
    }
}