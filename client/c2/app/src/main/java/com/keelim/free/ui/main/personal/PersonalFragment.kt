package com.keelim.free.ui.main.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.keelim.free.databinding.FragmentPersonalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalFragment : Fragment() {

    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        recycler.adapter = PersonalAdapter { url ->
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("공유하기")
                setMessage("어떤 팀에 공유를 하시겠습니까?")
                setPositiveButton("R.string.personal_delete_positive") { _, _ ->
//                    viewModel.share()

                }
                setNegativeButton("R.string.personal_delete_negative") { _, _ -> }
                show()
            }
        }
    }
}