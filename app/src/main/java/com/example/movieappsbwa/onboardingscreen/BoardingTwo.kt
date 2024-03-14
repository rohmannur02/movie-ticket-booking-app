package com.example.movieappsbwa.onboardingscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieappsbwa.auth.signin.SignIn
import com.example.movieappsbwa.databinding.ActivityBoardingTwoBinding

class BoardingTwo : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menangani klik tombol "Lanjutkan"
        binding.btnLanjutkan.setOnClickListener {
            navigateToNextScreen()
        }
        // Menangani klik tombol "Lewati"
        binding.btnLewati.setOnClickListener {
            navigateToSignInScreen()
        }
    }

    // Fungsi untuk pindah ke layar BoardingTwo
    private fun navigateToNextScreen() {
        val intent = Intent(this, BoardingThree::class.java)
        startActivity(intent)
    }

    // Fungsi untuk pindah ke layar SignIn
    private fun navigateToSignInScreen() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }
}