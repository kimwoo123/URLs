package com.keelim.free.ui.main.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.keelim.data.model.UrlState
import com.keelim.data.model.UrlState2
import com.keelim.free.databinding.FragmentPersonalBinding
import com.keelim.free.util.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast
import timber.log.Timber

@AndroidEntryPoint
class PersonalFragment : Fragment() {

    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonalViewModel by viewModels()
    private val personalAdapter by lazy {
        PersonalAdapter { url ->
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
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() = with(binding) {
        recycler.apply {
            setHasFixedSize(true)
            itemAnimator = SpringAddItemAnimator()
            adapter = personalAdapter.apply {
                doOnNextLayout {
                    submitList(emptyList())
                }
            }
        }
    }

    private fun observe() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED){
            viewModel.state.collect {
                when(it){
                    is UrlState2.Error -> {
                        showOff()
                    }
                    is UrlState2.Loading -> {
                        showOn()
                    }
                    is UrlState2.Success -> {
                        requireActivity().showToast(it.data.toString())
                        personalAdapter.submitList(it.data)
                    }
                    is UrlState2.UnInitialized -> {
                        requireActivity().showToast("로딩 중입니다.")
                    }
                }
            }
            viewModel.folder.collect {
                Timber.d("최종 값 $it")
                requireActivity().showToast(it.toString())
            }
        }
    }
    private fun showOff() = with(binding){
        progress.visibility = View.INVISIBLE
    }

    private fun showOn() = with(binding){
        progress.visibility = View.VISIBLE
    }
}