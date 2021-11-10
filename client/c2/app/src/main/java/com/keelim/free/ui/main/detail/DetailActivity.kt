package com.keelim.free.ui.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keelim.data.model.DataState
import com.keelim.free.R
import com.keelim.free.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater)}
    private val viewModel: DetailViewModel by viewModels()
    private val folderID by lazy { intent.getStringExtra("folder") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initData()
        observeState()
    }

    private fun initViews() = with(binding){

    }

    private fun initData(){
        viewModel.init(folderID!!)
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.state.collect {
                when(it){
                    is DataState.Error -> showToast(it.message)
                    is DataState.Loading -> showToast("로딩 중입니다.")
                    is DataState.Success -> showToast(it.data.toString())
                    is DataState.UnInitialized -> showToast("로딩 중입니다.")
                }
            }
        }
    }
}