package com.keelim.free.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
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

    private fun navController() = findNavController(R.id.nav_host_fragment_activity_auth)

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

    private fun initViews() = with(binding) {
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun initBio() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@AuthActivity,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT).show()
                }

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
                    Toast.makeText(this@AuthActivity, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
