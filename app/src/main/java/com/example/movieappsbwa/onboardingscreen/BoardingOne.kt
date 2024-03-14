package com.example.movieappsbwa.onboardingscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieappsbwa.auth.signin.SignIn
import com.example.movieappsbwa.databinding.ActivityBoardingOneBinding
import com.example.movieappsbwa.utils.Preference

class BoardingOne : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingOneBinding
    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference = Preference(this)

        handleOnboardingLogic()

        binding.btnLanjutkan.setOnClickListener {
            navigateToNextScreen()
        }

        binding.btnLewati.setOnClickListener {
            handleSkipOnboarding()
        }
    }

    // Handle logic when opening the screen
    private fun handleOnboardingLogic() {
        if (preference.getValue("onboarding") == "1") {
            finishAffinity()
            navigateToSignInScreen()
        }
    }

    // Move to the next onboarding screen
    private fun navigateToNextScreen() {
        val intent = Intent(this, BoardingTwo::class.java)
        startActivity(intent)
    }

    // Skip onboarding and move to the sign-in screen
    private fun handleSkipOnboarding() {
        preference.setValue("onboarding", "1")
        finishAffinity()
        navigateToSignInScreen()
    }

    // Move to the sign-in screen
    private fun navigateToSignInScreen() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }
}

