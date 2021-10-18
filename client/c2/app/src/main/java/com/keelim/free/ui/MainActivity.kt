package com.keelim.free.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.keelim.free.R
import com.keelim.free.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding){
        lifecycleScope.launch {
            mainViewModel.openText.collect {
                btn.text = it
            }
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//            }
        }
    }
}