package com.example.movieappsbwa.onboardingscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieappsbwa.auth.singup.SignUp
import com.example.movieappsbwa.databinding.ActivityBoardingThreeBinding

class BoardingThree : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menangani klik tombol "Lanjutkan"
        binding.btnMemulai.setOnClickListener {
            navigateToNextScreen()
        }
    }

    // Fungsi untuk pindah ke layar BoardingTwo
    private fun navigateToNextScreen() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
}