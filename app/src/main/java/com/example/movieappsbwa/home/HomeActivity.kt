package com.example.movieappsbwa.home

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movieappsbwa.R
import com.example.movieappsbwa.databinding.ActivityHomeBinding
import com.example.movieappsbwa.home.dashboard.DashboardFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentSetting = SettingFragment()
        setFragment(fragmentHome)

        binding.btnHome.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(binding.btnHome, R.drawable.ic_home_active)
            changeIcon(binding.btnTicket, R.drawable.ic_tiket)
            changeIcon(binding.btnSetting, R.drawable.ic_profile)
        }
        binding.btnTicket.setOnClickListener {
            setFragment(fragmentTicket)

            changeIcon(binding.btnHome, R.drawable.ic_home)
            changeIcon(binding.btnTicket, R.drawable.ic_tiket_active)
            changeIcon(binding.btnSetting, R.drawable.ic_profile)
        }
        binding.btnSetting.setOnClickListener {
            setFragment(fragmentSetting)

            changeIcon(binding.btnHome, R.drawable.ic_home)
            changeIcon(binding.btnTicket, R.drawable.ic_tiket)
            changeIcon(binding.btnSetting, R.drawable.ic_profile_active)
        }

    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }
}