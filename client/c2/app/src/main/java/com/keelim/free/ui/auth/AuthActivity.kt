package com.keelim.free.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.keelim.free.R
import com.keelim.free.databinding.ActivityAuthBinding
import com.keelim.free.ui.main.MenuActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tokenCheck()
        initViews()
    }

    private fun navController() = findNavController(R.id.nav_host_fragment_activity_auth)

    private fun tokenCheck() {
        val pref = getSharedPreferences("token", MODE_PRIVATE)
        val token = pref.getString("token", "")
        if (token != "") {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Timber.d("onSignInResult: ${response?.idpToken}")
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser

            val pref = getSharedPreferences("token",Context.MODE_PRIVATE)
            with (pref.edit()) {
                putString("token", user!!.email)
                commit()
            }

            startActivity(Intent(this@AuthActivity, MenuActivity::class.java))
            finish()
        } else {
            Timber.e("onSignInResult ${response!!.error}")
        }
    }

    private fun signIn() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun logout(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
            }
    }

    private fun initViews() = with(binding){
        signInButton.setOnClickListener {
            signIn()
        }
    }
}
