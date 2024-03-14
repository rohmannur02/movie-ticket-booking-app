package com.example.movieappsbwa.onboardingscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.movieappsbwa.databinding.ActivitySpalshScreenBinding

class SpalshScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed({
            // Intent untuk memulai aktivitas berikutnya setelah penundaan (dalam hal ini MainActivity)
            val intent = Intent(this, BoardingOne::class.java)
            startActivity(intent)
            // Menutup SplashScreen agar tidak bisa kembali saat tombol back ditekan
            finish()
        }, 3000) //

    }

}