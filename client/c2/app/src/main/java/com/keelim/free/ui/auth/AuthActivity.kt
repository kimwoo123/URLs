package com.keelim.free.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.keelim.free.R
import com.keelim.free.databinding.ActivityAuthBinding
import com.keelim.free.ui.main.MenuActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.Executor

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
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
        initBio()
        tokenCheck()
        initViews()
    }

    private fun tokenCheck() {
        val pref = getSharedPreferences("token", MODE_PRIVATE)
        val token = pref.getString("token", "")
        if (token != "") {
            biometricPrompt.authenticate(BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build())
        }
    }

    private fun logout(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
            }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Timber.d("onSignInResult: ${response?.idpToken}")
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val pref = getSharedPreferences("token",Context.MODE_PRIVATE)
            with (pref.edit()) {
                putString("token", response?.idpToken)
                commit()
            }

            startActivity(Intent(this@AuthActivity, MenuActivity::class.java))
            finish()
        } else {
            Timber.e("onSignInResult ${response?.error}")
        }
    }

    private fun signIn() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun initViews() = with(binding) {
        val tv = signInButton.getChildAt(0) as (android.widget.TextView)
        tv.text = "Please Add Google Login"
        signInButton.setOnClickListener {
            signIn()
        }

        authTitle.setOnClickListener{
            startActivity(Intent(this@AuthActivity, MenuActivity::class.java))
        }

        btnFinger.setOnClickListener{
            val pref = getSharedPreferences("token", MODE_PRIVATE)
            val token = pref.getString("token", "")
            if(token !=""){
                biometricPrompt.authenticate(BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric login for my app")
                    .setSubtitle("Log in using your biometric credential")
                    .setNegativeButtonText("Use account password")
                    .build())
            } else{
                Snackbar.make(binding.root, "최초 인증은 간편로그인을 사용하시기 바랍니다.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initBio() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@AuthActivity,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AuthActivity, MenuActivity::class.java))
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Snackbar.make(binding.root, "인증을 실패하였습니다.", Snackbar.LENGTH_SHORT).show()
                }
            })
    }
}
