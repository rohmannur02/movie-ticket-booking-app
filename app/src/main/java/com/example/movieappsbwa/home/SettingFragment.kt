package com.example.movieappsbwa.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieappsbwa.databinding.FragmentSettingBinding
import com.example.movieappsbwa.utils.Preference

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var preference: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = Preference(requireContext())

        binding.tvNama.text = preference.getValue("nama")
        binding.tvEmail.text = preference.getValue("email")

        Glide.with(this)
            .load(preference.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfile)
    }
}