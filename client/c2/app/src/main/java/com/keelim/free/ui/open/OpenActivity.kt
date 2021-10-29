package com.keelim.free.ui.open

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.keelim.free.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenActivity : AppCompatActivity() {
    private val openViewModel: OpenViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding){
        lifecycleScope.launch {
            openViewModel.openText.collect {
                btn.text = it
            }
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//            }
        }
    }
}