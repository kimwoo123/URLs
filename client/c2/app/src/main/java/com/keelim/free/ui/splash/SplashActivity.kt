package com.keelim.free.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.keelim.core.extensions.activityComposeView
import com.keelim.free.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComposeView {
            val isNextMove by viewModel.observe.collectAsState(false)
            SplashScreen(isLoading = !isNextMove) {
                goNext()
            }
        }
    }

    override fun onBackPressed() {}

    private fun goNext() {
        val pref = getSharedPreferences("checkFirst", MODE_PRIVATE)
        val checkFirst = pref.getBoolean("checkFirst", false)
        if (checkFirst.not()) {
            val editor = pref.edit().apply {
                putBoolean("checkFirst", true)
                commit()
            }
            startActivity(Intent(this, TutorialActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}