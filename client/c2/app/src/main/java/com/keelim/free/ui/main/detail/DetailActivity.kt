package com.keelim.free.ui.main.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.doOnNextLayout
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearSnapHelper
import coil.load
import com.keelim.data.model.DataState
import com.keelim.free.R
import com.keelim.free.databinding.ActivityDetailBinding
import com.keelim.free.util.SpringAddItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import showToast

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater)}
    private val viewModel: DetailViewModel by viewModels()
    private val folderID by lazy { intent.getStringExtra("folder") }
    private val detailAdapter by lazy{
        DetailAdapter{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initData()
        observeState()
    }

    private fun initViews() = with(binding){
        detailRecycler.apply {
            itemAnimator = SpringAddItemAnimator()
            adapter = detailAdapter.apply {
                doOnNextLayout {

                }
            }
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(detailRecycler)
    }

    private fun initData(){
        showToast("폴더 아이디: $folderID")
        viewModel.init(folderID!!)
    }

    private fun observeState() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.state.collect {
                when(it){
                    is DataState.Error -> showToast(it.message)
                    is DataState.Loading -> showToast("로딩 중입니다.")
                    is DataState.Success -> {
                        if(it.data.isEmpty()){
                            binding.tvNoData.visibility = View.VISIBLE
                        }
                        detailAdapter.submitList(it.data)
                    }
                    is DataState.UnInitialized -> showToast("로딩 중입니다.")
                }
            }
        }
    }
}